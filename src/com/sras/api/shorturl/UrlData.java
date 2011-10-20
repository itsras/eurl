package com.sras.api.shorturl;

import java.sql.Timestamp;

public interface UrlData {

    public long getId();

    public String getLongUrl();

    public String getShortUrl();

    public String getEncryptedUrl();

    public String getKey();

    public boolean isPrivacy();

    public Timestamp getCreated();

    public Timestamp getUpdated();

    public void setId(long id);

    public void setLongUrl(String longUrl);

    public void setEncryptedUrl(String encryptedUrl);

    public void setKey(String key);

    public void setPrivacy(boolean privacy);

    public void setCreated(Timestamp created);

    public void setUpdated(Timestamp updated);

    public long getUserId();

    public void setUserId(long userId);

}
