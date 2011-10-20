package com.sras.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sras.database.exceptions.InvalidSQLValueException;
import com.sras.datamodel.DataModel;

/**
 * Created by IntelliJ IDEA. User: admin Date: Jun 1, 2010 Time: 9:26:25 AM To
 * change this template use File | Settings | File Templates.
 */
public final class DBConnectionUtils {
    private static ConnectionPoolManager poolManager;
    private static Log log = LogFactory.getLog(DBConnectionUtils.class);

    static {
	poolManager = ConnectionPoolManager.getProdInstance();
    }

    public static Connection getConnection() throws SQLException {
	return poolManager.mySQLConnection();
    }

    @SuppressWarnings("static-access")
    public static void close(Connection connection) throws SQLException {
	poolManager.returnConnection(connection);
    }

    @SuppressWarnings("unchecked")
    public static int execute(String sql, ArrayList bindVars)
	    throws SQLException {
	log.info("SQL EXECUTE: " + sql);
	Connection connection = getConnection();
	PreparedStatement pstatement = connection.prepareStatement(sql,
		Statement.RETURN_GENERATED_KEYS);
	if (bindVars != null) {
	    bindVariables(pstatement, bindVars.toArray());
	}
	pstatement.executeUpdate();
	ResultSet rs = pstatement.getGeneratedKeys();
	int id = rs.next() ? rs.getInt(1) : -1;
	rs.close();
	close(connection);
	return id;
    }

    @SuppressWarnings("unchecked")
    private static ResultSet read(String sql, ArrayList bindVars,
	    Connection connection) throws SQLException {
	log.info("SQL READ: " + sql);
	PreparedStatement pstatement = connection.prepareStatement(sql);
	if (bindVars != null) {
	    bindVariables(pstatement, bindVars.toArray());
	}
	return pstatement.executeQuery();
    }

    @SuppressWarnings("unchecked")
    public static DataModel read(DataModel dm, ArrayList bindVars)
	    throws SQLException {
	log.info("SQL READ (overloaded): " + dm.getReadQuery());
	Connection connection = getConnection();
	ResultSet rs = read(dm.getReadQuery(), bindVars, connection);
	DataModel newObj = null;
	if (rs != null && rs.next()) {
	    try {
		newObj = (DataModel) dm.getClass().newInstance();
	    } catch (Exception e) {
		throw new SQLException(e.getMessage());
	    }
	    dm.fillResult(rs, newObj);
	}
	close(connection);
	return newObj;
    }

    @SuppressWarnings("unchecked")
    public static Collection<DataModel> enumerate(DataModel dm,
	    ArrayList bindVars) throws SQLException {
	log.info("SQL ENUMERATE: " + dm.getEnumerateQuery());
	Collection<DataModel> list = null;
	Connection connection = getConnection();
	ResultSet rs = read(dm.getEnumerateQuery(), bindVars, connection);
	if (rs != null) {
	    list = new ArrayList<DataModel>();
	    while (rs.next()) {
		DataModel newObj = null;
		try {
		    newObj = dm.getClass().newInstance();
		} catch (Exception e) {
		    throw new SQLException(e.getMessage());
		}
		dm.fillResult(rs, newObj);
		list.add(newObj);
	    }
	}
	close(connection);
	return list;
    }

    @SuppressWarnings("unchecked")
    public static DataModel count(DataModel dm, ArrayList bindVars)
	    throws SQLException {
	log.info("SQL COUNT: " + dm.getCountQuery());
	Connection connection = getConnection();
	ResultSet rs = read(dm.getCountQuery(), bindVars, connection);
	DataModel newObj = null;
	if (rs != null && rs.next()) {
	    try {
		newObj = (DataModel) dm.getClass().newInstance();
	    } catch (Exception e) {
		throw new SQLException(e.getMessage());
	    }
	    dm.fillResult(rs, newObj);
	}
	close(connection);
	return newObj;
    }

    @SuppressWarnings("unchecked")
    public static int create(DataModel dm, ArrayList bindVars)
	    throws SQLException {
	int id = execute(dm.getCreateQuery(), bindVars);
	return id;
    }

    @SuppressWarnings("unchecked")
    public static int update(DataModel dm, ArrayList bindVars)
	    throws SQLException {
	int state = execute(dm.getUpdateQuery(), bindVars);
	return state;
    }

    @SuppressWarnings("unchecked")
    public static int delete(DataModel dm, ArrayList bindVars)
	    throws SQLException {
	int state = execute(dm.getDeleteQuery(), bindVars);
	return state;
    }

    private static void bindVariables(PreparedStatement statement, Object[] data)
	    throws SQLException {
	for (int i = 0; i < data.length; i++) {
	    if (data[i] == null) {
		log.error("NULL SQLDataType object in bindVariables");
		throw new InvalidSQLValueException(
			"NULL SQLDataType object in bindVariables");
	    }

	    if (!(data[i] instanceof SQLDataType)) {
		log.error("Bind variable type " + data[i].getClass().getName()
			+ " should be of type SQLDataType");
		throw new InvalidSQLValueException("Bind variable type "
			+ data[i].getClass().getName()
			+ " should be of type SQLDataType");
	    }
	    SQLDataType val = (SQLDataType) data[i];
	    int type = val.getType(); // java.sql.Types
	    Object obj = val.getValue();

	    if (val.isNull()) {
		statement.setNull(i + 1, val.getType());
	    } else if (type == Types.BINARY) {
		try {
		    if (obj instanceof InputStream) {
			InputStream stream = (InputStream) val.getValue();
			statement.setBinaryStream(i + 1, stream, stream
				.available());
		    } else if (obj instanceof byte[]) {
			statement.setBytes(i + 1, (byte[]) obj);
		    }
		} catch (IOException e) {
		    log.error("Failure to retrieve SQL BinaryStream value");
		    throw new InvalidSQLValueException(
			    "Failure to retrieve SQL BinaryStream value");
		}
	    } else if (type == java.sql.Types.VARCHAR) {
		statement.setString(i + 1, obj.toString());
	    } else if (obj instanceof Short)
	    // Oracle-ism. Need to deal with short specially
	    {
		statement.setLong(i + 1, ((Short) obj).intValue());
	    } else {
		statement.setObject(i + 1, obj);
	    }
	}
    }
}
