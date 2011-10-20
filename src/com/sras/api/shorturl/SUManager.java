package com.sras.api.shorturl;

import java.util.Collection;

import com.sras.api.common.PageData;

public interface SUManager
{
    public String shortUrl(String longUrl);

    public UrlStatsData getStats(String shortUrl);

    public String encrypt(String longUrl);
    
    public String decrypt(String encodedUrl);

    public UrlData read(String shortUrl);
    
    public Collection<UrlData> getShortUrls(PageData pageData, boolean showAll);
    
    public boolean save(UrlData urlData);
    
    public void delete(UrlData urlData);
}
