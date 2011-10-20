package com.sras.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Jun 1, 2010
 * Time: 4:56:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataModel
{
	public abstract String getReadQuery();

    public abstract String getEnumerateQuery();

    public abstract String getDeleteQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract void fillResult(ResultSet rs, DataModel dm) throws SQLException;

    public String getCountQuery()
    {
        return null;
    }
}
