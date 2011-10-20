package com.sras.api.common.impl;

import java.sql.Timestamp;

import com.sras.api.common.UserData;
import com.sras.datamodel.USERData;

public class UserDataImpl implements UserData
{
	private long id;
	private String userName;
	private String passWord;
	private String email;
	private String mobileNumber;
	private String apiKey;
	private boolean isAdmin;
	private Timestamp created;
	private Timestamp updated;

	public UserDataImpl()
	{

	}

	public UserDataImpl(long id, String userName, String passWord,
			String email, String mobileNumber, String apiKey, boolean isAdmin,
			Timestamp created, Timestamp updated)
	{
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.apiKey = apiKey;
		this.isAdmin = isAdmin;
		this.created = created;
		this.updated = updated;
	}

	public UserDataImpl(USERData uData)
	{
		this.id = uData.getId();
		this.userName = uData.getUserName();
		this.passWord = uData.getPassWord();
		this.email = uData.getEmail();
		this.mobileNumber = uData.getMobileNumber();
		this.apiKey = uData.getApiKey();
		this.isAdmin = uData.isAdmin();
	}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
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
}
