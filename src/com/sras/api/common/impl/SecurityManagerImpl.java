package com.sras.api.common.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sras.api.common.SecurityManager;
import com.sras.api.common.UserData;
import com.sras.client.utils.EncryptUtils;
import com.sras.database.DBConnectionUtils;
import com.sras.database.SQLDataType;
import com.sras.datamodel.DataModel;
import com.sras.datamodel.USERData;

public class SecurityManagerImpl implements SecurityManager {
    private static Logger logger = Logger.getLogger(SecurityManagerImpl.class
	    .getName());

    public SecurityManagerImpl() {

    }

    public long createUser(UserData userData) {
	long userId = 0;
	String xorMsg = EncryptUtils.xorMessage(userData.getPassWord());
	String encodedPassword = EncryptUtils.base64encode(xorMsg);

	// NAME, PASSWORD, EMAIL, MOBILE_NUMBER, API_KEY, CREATED
	USERData user = new USERData();
	ArrayList<SQLDataType> bindVars = new ArrayList<SQLDataType>();
	bindVars.add(SQLDataType.String(userData.getUserName()));
	bindVars.add(SQLDataType.String(encodedPassword));
	bindVars.add(SQLDataType.String(userData.getEmail()));
	bindVars.add(SQLDataType.String(userData.getMobileNumber()));
	bindVars.add(SQLDataType.String(userData.getApiKey()));
	bindVars.add(SQLDataType.Timestamp(userData.getCreated()));
	try {
	    userId = DBConnectionUtils.create(user, bindVars);
	    logger.info("Generated ID for " + userData.getUserName() + " :: "
		    + userId);
	} catch (SQLException e) {
	    logger.error("SQL ERROR :: Failed while creating user "
		    + userData.getUserName());
	    return userId;
	}
	return userId;
    }

    public UserData readUser(long id) {
	USERData user = new USERData();
	user.setId(id);
	try {
	    DataModel dm = DBConnectionUtils.read(user, null);
	    user = (USERData) dm;
	    if (user != null) {
		//String password = EncryptUtils.xorMessage(EncryptUtils.base64decode(user.getPassWord()))
		return new UserDataImpl(user);
	    }
	} catch (SQLException e) {
	    logger
		    .error("SQL ERROR :: Failed while reading user details for id "
			    + id);
	    return null;
	}
	return null;
    }
}