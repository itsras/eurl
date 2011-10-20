package com.sras.api.shorturl.impl;

import java.sql.Timestamp;

import com.sras.api.shorturl.UrlData;
import com.sras.client.utils.BaseConverterUtils;
import com.sras.datamodel.URLData;

public class UrlDataImpl implements UrlData
{

	private long id;
	private String longUrl;
	private String encryptedUrl;
	private String key;
	private boolean privacy; // can be PUBLIC or PRIVATE
	private Timestamp created;
	private Timestamp updated;
	private long userId;

	public UrlDataImpl()
	{

	}

	public UrlDataImpl(URLData uData)
	{
		this.id = uData.getId();
		this.longUrl = uData.getUrl();
		this.key = uData.getKey();
		this.encryptedUrl = uData.getEncodedStr();
		this.userId = uData.getUserId();
	}

	@Override
	public String getEncryptedUrl()
	{
		return this.encryptedUrl;
	}

	@Override
	public long getId()
	{
		return this.id;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}

	@Override
	public String getLongUrl()
	{
		return this.longUrl;
	}

	@Override
	public String getShortUrl()
	{
		return BaseConverterUtils.toBase62(this.id);
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setLongUrl(String longUrl)
	{
		this.longUrl = longUrl;
	}

	public void setEncryptedUrl(String encryptedUrl)
	{
		this.encryptedUrl = encryptedUrl;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public boolean isPrivacy()
	{
		return privacy;
	}

	public void setPrivacy(boolean privacy)
	{
		this.privacy = privacy;
	}

	public Timestamp getCreated()
	{
		return created;
	}

	public void setCreated(Timestamp created)
	{
		this.created = created;
	}

	public Timestamp getUpdated()
	{
		return updated;
	}

	public void setUpdated(Timestamp updated)
	{
		this.updated = updated;
	}

	public long getUserId() {
	    return userId;
	}

	public void setUserId(long userId) {
	    this.userId = userId;
	}
}
