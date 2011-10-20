package com.sras.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sras.database.SQLDataType;

/**
 * Created by IntelliJ IDEA. User: kittu Date: 14 Jun, 2010 Time: 10:10:33 PM To
 * change this template use File | Settings | File Templates.
 */
public class URLData extends DataModel
{
	private static final String TABLE_NAME = "URL_DATA";
	private PAGEData paginationDetails;
	private long id;
	private String url;
	private String encodedStr;
	private String key;
	private Boolean privacy; // can be PUBLIC or PRIVATE
	private Timestamp created;
	private Timestamp updated;
	private long userId = 0;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getEncodedStr()
	{
		return encodedStr;
	}

	public void setEncodedStr(String encodedStr)
	{
		this.encodedStr = encodedStr;
	}

	public String getKey()
	{
		return key;
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

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public PAGEData getPaginationDetails()
	{
		return paginationDetails;
	}

	public void setPaginationDetails(PAGEData paginationDetails)
	{
		this.paginationDetails = paginationDetails;
	}

	public String getReadQuery()
	{
		String sql = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE_NAME
				+ " WHERE 1=1 ";
		if (getEncodedStr() != null)
		{
			sql += " AND ENCODED_STR = '" + getEncodedStr() + "'";
		}
		if (getId() > 0)
		{
			sql += " AND ID = " + getId();
		}
		if (getUserId() > 0)
		{
			sql += " AND USER_ID = " + getUserId();
		}
		return sql;
	}

	public String getEnumerateQuery()
	{
		if (getPaginationDetails() != null)
		{
			long pageNumber = getPaginationDetails().getPageNumber();
			long rowsPerPage = getPaginationDetails().getRecordsPerPage();
			String sortOrder = getPaginationDetails().isSortOrder() ? " ASC "
					: " DESC ";
			String sortColumn = getPaginationDetails().getColumn();
			sortColumn = (sortColumn == null || sortColumn.trim().length() == 0) ? " CREATED "
					: sortColumn;
			pageNumber = (pageNumber <= 0) ? 1 : pageNumber;
			rowsPerPage = (rowsPerPage <= 0) ? 10 : rowsPerPage;
			long limit = ((pageNumber - 1) * rowsPerPage);
			limit = (limit < 0) ? 0 : limit;
			StringBuffer paginatedQuery = new StringBuffer("SELECT "
					+ SELECT_COLUMNS + " FROM " + TABLE_NAME + " where 1=1 ");
			if (getUserId() > 0)
			{
				paginatedQuery.append(" AND USER_ID = " + getUserId());
			}
			paginatedQuery.append(" ORDER BY " + sortColumn + sortOrder
					+ " LIMIT " + limit + ", " + rowsPerPage);
			return paginatedQuery.toString();
		}
		else
		{
			StringBuffer query = new StringBuffer("SELECT " + SELECT_COLUMNS
					+ " FROM " + TABLE_NAME + " WHERE 1=1 ");
			if (getUserId() > 0)
			{
				query.append(" AND USER_ID = " + getUserId());
			}
			query.append(" ORDER BY CREATED DESC");
			return query.toString();
		}
	}

	public String getDeleteQuery()
	{
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE 1=1";
		if (getEncodedStr() != null)
		{
			sql += " AND ENCODED_STR = '" + getEncodedStr() + "'";
		}
		if (getId() > 0)
		{
			sql += " AND ID = " + getId();
		}
		if (getUserId() > 0)
		{
			sql += " AND USER_ID = " + getUserId();
		}
		return sql;
	}

	public String getCreateQuery()
	{
		return "INSERT INTO " + TABLE_NAME + " (" + INSERT_COLUMNS
				+ ") VALUES (?, ?, ?, ?, ?, ?)";
	}

	public String getUpdateQuery()
	{
		String sql = "UPDATE " + TABLE_NAME + " SET ";
		if (privacy != null)
		{
			sql += " PRIVACY = " + (privacy ? 1 : 0);
		}
		if (key != null)
		{
			sql += " KEY_STR = " + key;
		}
		if (url != null)
		{
			sql += " URL_STR = " + url;
		}
		if (encodedStr != null)
		{
			sql += " ENCODED_STR = " + encodedStr;
		}
		sql += " WHERE ID = " + getId();
		return sql;
	}

	public void fillResult(ResultSet rs, DataModel dm) throws SQLException
	{
		URLData user = (URLData) dm;
		user.setId(SQLDataType.Long(rs, 1));
		user.setEncodedStr(SQLDataType.String(rs, 2));
		user.setUrl(SQLDataType.String(rs, 3));
		user.setKey(SQLDataType.String(rs, 4));
		user.setCreated(SQLDataType.Timestamp(rs, 5));
		user.setUpdated(SQLDataType.Timestamp(rs, 6));
		user.setPrivacy(SQLDataType.Boolean(rs, 7));
		user.setUserId(SQLDataType.Long(rs, 8));
	}

	private static final String SELECT_COLUMNS = "ID,ENCODED_STR,URL_STR,KEY_STR,CREATED,MODIFIED,PRIVACY,USER_ID";
	private static final String INSERT_COLUMNS = "ENCODED_STR,URL_STR,KEY_STR,CREATED,PRIVACY,USER_ID";
}
