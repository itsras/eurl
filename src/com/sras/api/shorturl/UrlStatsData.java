package com.sras.api.shorturl;

public interface UrlStatsData {

    public long getClicksCount();

    public void setClicksCount(long clicksCount);

    public long getClicksCountByOthers();

    public void setClicksCountByOthers(long clicksCountByOthers);

    public long getFbLikes();

    public void setFbLikes(long fbLikes);

    public long getTotalClicksCount();

    public void setTotalClicksCount(long totalClicksCount);
}
