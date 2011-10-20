package com.sras.api.shorturl.impl;

import com.sras.api.shorturl.UrlStatsData;

public class UrlStatsDataImpl implements UrlStatsData {
    private long urlId;
    private String shortUrl;
    private long clicksCount;
    private long clicksCountByOthers;
    private long fbLikes;
    private long totalClicksCount;

    public UrlStatsDataImpl() {
    }

    public long getUrlId() {
        return urlId;
    }

    public void setUrlId(long urlId) {
        this.urlId = urlId;
    }

    public String getShortUrl() {
	return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
	this.shortUrl = shortUrl;
    }

    public long getClicksCount() {
	return clicksCount;
    }

    public void setClicksCount(long clicksCount) {
	this.clicksCount = clicksCount;
    }

    public long getClicksCountByOthers() {
	return clicksCountByOthers;
    }

    public void setClicksCountByOthers(long clicksCountByOthers) {
	this.clicksCountByOthers = clicksCountByOthers;
    }

    public long getFbLikes() {
	return fbLikes;
    }

    public void setFbLikes(long fbLikes) {
	this.fbLikes = fbLikes;
    }

    public long getTotalClicksCount() {
	return totalClicksCount;
    }

    public void setTotalClicksCount(long totalClicksCount) {
	this.totalClicksCount = totalClicksCount;
    }
}
