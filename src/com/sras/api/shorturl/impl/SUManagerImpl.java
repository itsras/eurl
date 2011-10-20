package com.sras.api.shorturl.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.validator.UrlValidator;
import org.apache.log4j.Logger;

import com.sras.api.common.PageData;
import com.sras.api.common.UserData;
import com.sras.api.shorturl.SUManager;
import com.sras.api.shorturl.UrlData;
import com.sras.api.shorturl.UrlStatsData;
import com.sras.client.utils.BaseConverterUtils;
import com.sras.client.utils.EncryptUtils;
import com.sras.client.utils.HelperUtilities;
import com.sras.database.DBConnectionUtils;
import com.sras.database.SQLDataType;
import com.sras.datamodel.DataModel;
import com.sras.datamodel.URLData;

public class SUManagerImpl implements SUManager {
    private static Logger logger = Logger.getLogger(SUManagerImpl.class
	    .getName());
    private UserData userData;

    public SUManagerImpl(UserData userData) {
	this.userData = userData;
    }

    public Collection<UrlData> getShortUrls(PageData pageData, boolean showAll) {
	Collection<UrlData> shortUrls = new ArrayList<UrlData>();
	Collection<DataModel> urls = new ArrayList<DataModel>();
	try {
	    URLData urlData = new URLData();
	    if (!showAll) {
		urlData.setUserId(userData.getId());
	    }
	    if (pageData != null) {
		com.sras.datamodel.PAGEData pData = new com.sras.datamodel.PAGEData(
			pageData.getPageNumber(), pageData.getRecordsPerPage(),
			pageData.getColumn(), pageData.isSortOrder());
		urlData.setPaginationDetails(pData);
	    }
	    urls = (Collection<DataModel>) DBConnectionUtils.enumerate(urlData,
		    null);
	} catch (SQLException e) {
	    logger.error("SQL ERROR :: Failed while fetching all urls");
	}
	for (DataModel dm : urls) {
	    URLData ud = (URLData) dm;
	    UrlData urlData = new UrlDataImpl(ud);
	    urlData.setCreated(ud.getCreated());
	    urlData.setUpdated(ud.getUpdated());
	    urlData.setPrivacy(ud.isPrivacy());
	    shortUrls.add(urlData);
	}
	return shortUrls;
    }

    public UrlStatsData getStats(String shortUrl) {
	return new UrlStatsDataImpl();
    }

    public UrlData read(String shortUrl) {
	URLData ud = new URLData();
	try {
	    long id = BaseConverterUtils.fromBase62(shortUrl);
	    ud.setId(id);
	    ud.setUserId(userData.getId());
	    ud = (URLData) DBConnectionUtils.read(ud, null);
	} catch (SQLException e) {

	    logger.error("SQL ERROR :: Failed while fetching long url for "
		    + shortUrl);
	}
	return new UrlDataImpl(ud);
    }

    public String shortUrl(String longUrl) {
	URLData urlData = new URLData();
	try {
	    urlData.setUrl(longUrl);
	    urlData.setUserId(userData.getId());
	    urlData = (URLData) DBConnectionUtils.read(urlData, null);
	} catch (SQLException e) {

	    logger.error("SQL ERROR :: Failed while fetching short url for "
		    + longUrl);
	}
	return BaseConverterUtils.toBase62(urlData.getId());
    }

    public boolean save(UrlData urlData) {
	String longUrl = urlData.getLongUrl();
	if (longUrl == null || longUrl.trim().length() == 0) {
	    logger.error("INVALID URL :: Url cannot be null");
	    return false;
	}
	String encoded = encrypt(longUrl);
	if (encoded == null) {
	    return false;
	}
	encoded = HelperUtilities.escapeChars(encoded);
	java.sql.Date date = new java.sql.Date(urlData.getCreated().getTime());
	ArrayList<SQLDataType> bindVars = new ArrayList<SQLDataType>();
	bindVars.add(SQLDataType.String(encoded));
	bindVars.add(SQLDataType.String(longUrl));
	bindVars.add(SQLDataType.String(EncryptUtils.key));
	bindVars.add(SQLDataType.Timestamp(date));
	bindVars.add(SQLDataType.Boolean(urlData.isPrivacy()));
	bindVars.add(SQLDataType.Long(userData.getId()));
	URLData url = new URLData();
	try {
	    long id = DBConnectionUtils.create(url, bindVars);
	    logger.info("Generated ID for " + longUrl + " :: " + id);
	} catch (SQLException e) {
	    logger.error("SQL ERROR :: Failed while saving url " + longUrl);
	    return false;
	}
	return true;
    }

    public String decrypt(String encodedUrl) {
	try {
	    encodedUrl = HelperUtilities.escapeChars(encodedUrl);
	    URLData urlData = new URLData();
	    urlData.setEncodedStr(encodedUrl);
	    urlData.setUserId(userData.getId());
	    urlData = (URLData) DBConnectionUtils.read(urlData, null);
	    return urlData.getUrl();
	} catch (Exception e) {
	    logger.error("SQL ERROR :: Failed while decrypting url "
		    + encodedUrl);
	}
	return null;
    }

    public String encrypt(String longUrl) {
	longUrl = (!longUrl.contains("//")) ? "http://" + longUrl : longUrl;

	String[] schemes = { "http", "https", "ftp" };
	UrlValidator validator = new UrlValidator(schemes);
	if (!validator.isValid(longUrl)) {
	    logger.error("INVALID URL :: Failed while encrypting url "
		    + longUrl);
	    return null;
	}
	logger.info("URL STRING :: " + longUrl);

	String xorMsg = EncryptUtils.xorMessage(longUrl);
	String encoded = EncryptUtils.base64encode(xorMsg);
	return encoded;
    }

    public void delete(UrlData urlData) {
	String shortUrl = urlData.getShortUrl();
	if (shortUrl == null || shortUrl.trim().length() == 0) {
	    logger.error("INVALID URL :: Short url cannot be null");
	    return;
	}
	URLData ud = new URLData();
	try {
	    long id = BaseConverterUtils.fromBase62(shortUrl);
	    ud.setId(id);
	    ud.setUserId(userData.getId());
	    DBConnectionUtils.delete(ud, null);
	} catch (SQLException e) {

	    logger.error("SQL ERROR :: Failed while deleting short url for "
		    + shortUrl);
	}
    }
}