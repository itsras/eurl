package com.sras.api.common;

import java.sql.Timestamp;

public interface UserData
{
	public long getId();

	public void setId(long id);

	public String getUserName();

	public void setUserName(String userName);

	public String getPassWord();

	public void setPassWord(String passWord);

	public String getEmail();

	public void setEmail(String email);

	public String getMobileNumber();

	public void setMobileNumber(String mobileNumber);

	public String getApiKey();

	public void setApiKey(String apiKey);

	public boolean isAdmin();

	public void setAdmin(boolean isAdmin);

	public Timestamp getCreated();

	public Timestamp getUpdated();

	public void setCreated(Timestamp created);

	public void setUpdated(Timestamp updated);
}
