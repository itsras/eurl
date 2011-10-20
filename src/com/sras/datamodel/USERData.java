package com.sras.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sras.database.SQLDataType;

public class USERData extends DataModel {
    private static final String TABLE_NAME = "USER_DATA";
    private long id;
    private String userName;
    private String passWord;
    private String email;
    private String mobileNumber;
    private String apiKey;
    private boolean isAdmin;
    private Timestamp created;
    private Timestamp updated;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getPassWord() {
	return passWord;
    }

    public void setPassWord(String passWord) {
	this.passWord = passWord;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getMobileNumber() {
	return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    public String getApiKey() {
	return apiKey;
    }

    public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
    }

    public boolean isAdmin() {
	return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    public Timestamp getCreated() {
	return created;
    }

    public void setCreated(Timestamp created) {
	this.created = created;
    }

    public Timestamp getUpdated() {
	return updated;
    }

    public void setUpdated(Timestamp updated) {
	this.updated = updated;
    }

    public String getReadQuery() {
	StringBuffer sql = new StringBuffer("SELECT " + COLUMNS + " FROM "
		+ TABLE_NAME + " WHERE 1=1 ");
	if (getUserName() != null) {
	    sql.append(" AND NAME = '" + getUserName() + "'");
	}
	if (getId() > 0) {
	    sql.append(" AND ID = " + getId());
	}
	return sql.toString();
    }

    public String getEnumerateQuery() {
	return null;
    }

    public String getDeleteQuery() {
	String sql = null;
	if (getUserName() != null) {
	    sql = "DELETE from " + TABLE_NAME + " where ID = ? ";
	} else {
	    sql = "DELETE from " + TABLE_NAME;
	}
	return sql;
    }

    public String getCreateQuery() {
	return "INSERT INTO " + TABLE_NAME + " (" + INSERT_COLUMNS
		+ ") VALUES (?, ?, ?, ?, ?, ?)";
    }

    public String getUpdateQuery() {
	String sql = "UPDATE " + TABLE_NAME + " SET ";
	if (getPassWord() != null) {
	    sql += " PASSWORD = ? ";
	}
	if (getEmail() != null) {
	    sql += ", EMAIL = ? ";
	}
	if (getMobileNumber() != null) {
	    sql += ", MOBILE_NUMBER = ? ";
	}
	if (getApiKey() != null) {
	    sql += ", API_KEY = ? ";
	}

	if (getUserName() != null) {
	    sql += " WHERE NAME = ? ";
	} else if (getId() > 0) {
	    sql += " WHERE ID = ? ";
	}
	return sql;
    }

    public void fillResult(ResultSet rs, DataModel dm) throws SQLException {
	USERData user = (USERData) dm;
	user.setId(SQLDataType.Long(rs, 1));
	user.setUserName(SQLDataType.String(rs, 2));
	user.setPassWord(SQLDataType.String(rs, 3));
	user.setEmail(SQLDataType.String(rs, 4));
	user.setMobileNumber(SQLDataType.String(rs, 5));
	user.setApiKey(SQLDataType.String(rs, 6));
	user.setAdmin((SQLDataType.Integer(rs, 7) == 1) ? true : false);
	user.setCreated(SQLDataType.Timestamp(rs, 8));
	user.setUpdated(SQLDataType.Timestamp(rs, 9));
    }

    private static final String COLUMNS = "ID, NAME, PASSWORD, EMAIL, MOBILE_NUMBER, API_KEY, ROLE, CREATED, MODIFIED";
    private static final String INSERT_COLUMNS = "NAME, PASSWORD, EMAIL, MOBILE_NUMBER, API_KEY, CREATED";
}
