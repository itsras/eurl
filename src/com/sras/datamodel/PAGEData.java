package com.sras.datamodel;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Jun 1, 2010
 * Time: 6:12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class PAGEData
{
    private String column;
    private long pageNumber = 1;
    private long recordsPerPage = 10;
    private boolean sortOrder = false;

    public PAGEData(long pageNumber, long recordsPerPage, String column, boolean sortOrder)
    {
        this.pageNumber = pageNumber;
        this.recordsPerPage = recordsPerPage;
        this.column = column;
        this.sortOrder = sortOrder;
    }

    public String getColumn()
    {
        return column;
    }

    public void setColumn(String column)
    {
        this.column = column;
    }

    public long getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public long getRecordsPerPage()
    {
        return recordsPerPage;
    }

    public void setRecordsPerPage(long recordsPerPage)
    {
        this.recordsPerPage = recordsPerPage;
    }

    public boolean isSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(boolean sortOrder)
    {
        this.sortOrder = sortOrder;
    }
}
