package com.sras.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 * Created by IntelliJ IDEA. User: admin Date: Jun 4, 2010 Time: 3:58:56 PM To
 * change this template use File | Settings | File Templates.
 */
public class ConnectionPoolManager {
    private static Log logger = LogFactory.getLog(ConnectionPoolManager.class);
    private static boolean initialized = false;
    // private String url =
    // "jdbc:mysql://127.0.0.1:3306/encrypturls?autoReconnect=true";
    // private String driverName = "com.mysql.jdbc.Driver";
    // private String host = "127.0.0.1";
    // private String port = "3306";
    // private String user = "root";
    // private String password = "password";
    // private String instance = "encrypturls";
    private String url;
    private String driverName;
    private String host;
    private String port;
    private String user;
    private String password;
    private String instance;
    private String maxActive;
    private String maxIdle;
    private String maxWait;
    private String ttl;
    private boolean isLoaded = false;

    private static BasicDataSource bds;
    private static final String VALIDATION_QUERY = "SELECT 1";

    private static ConnectionPoolManager stressInstance = new ConnectionPoolManager();

    private ConnectionPoolManager() {

    }

    public boolean isLoaded() {
	return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
	this.isLoaded = isLoaded;
    }

    public String getTtl() {
	return ttl;
    }

    public void setTtl(String ttl) {
	this.ttl = ttl;
    }

    public static ConnectionPoolManager getProdInstance() {
	return stressInstance;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getDriverName() {
	return driverName;
    }

    public void setDriverName(String driverName) {
	this.driverName = driverName;
    }

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public String getPort() {
	return port;
    }

    public void setPort(String port) {
	this.port = port;
    }

    public String getInstance() {
	return instance;
    }

    public void setInstance(String instance) {
	this.instance = instance;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getMaxActive() {
	return maxActive;
    }

    public void setMaxActive(String maxActive) {
	this.maxActive = maxActive;
    }

    public String getMaxIdle() {
	return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
	this.maxIdle = maxIdle;
    }

    public String getMaxWait() {
	return maxWait;
    }

    public void setMaxWait(String maxWait) {
	this.maxWait = maxWait;
    }

    /**
     * Gets the connection from the given Database type
     * 
     * @param connectionType
     *            Type of DB to connect to
     * @return Connection db connection instance
     * @throws Exception
     *             app specific exception
     */
    public Connection getConnection(int connectionType) throws Exception {
	logger.debug("Inside the getConnection of "
		+ ConnectionPoolManager.class.getSimpleName());
	switch (connectionType) {
	case 1:
	    return mySQLConnection();
	default:
	    logger.error("Invalid DataBase");
	    return null;
	}

    }

    /**
     * Closes the connection (inturn returns the connection to the
     * ConnectionPool)
     * 
     * @param conn
     *            db connection instance
     */
    public static void returnConnection(Connection conn) {
	try {
	    if (conn != null)
		conn.close();

	} catch (SQLException sql) {
	    logger.error("Unable to close connection object", sql);
	}
    }

    /**
     * Connection object from mysql
     * 
     * @return Connection to the DB
     * @throws Exception
     *             application specific exception
     */
    public Connection mySQLConnection() throws SQLException {
	logger
		.debug("Inside mySQLConnection method, trying to acquire MYSQL connection");
	if (!initialized || bds == null)
	    initializer();

	Connection conn;
	try {
	    conn = bds.getConnection();
	} catch (SQLException sql) {
	    logger.error("Unable to get connection ..", sql);
	    throw sql;
	}
	return conn;
    }

    /**
     * @throws Exception
     *             app specific exception
     */
    public void initializer() throws SQLException {
	logger.info("Inside initializer method");

	if (driverName == null || host == null || port == null
		|| instance == null || user == null || password == null
		|| driverName.trim().equalsIgnoreCase("")
		|| host.trim().equalsIgnoreCase("")
		|| port.trim().equalsIgnoreCase("")
		|| instance.trim().equalsIgnoreCase("")
		|| user.trim().equalsIgnoreCase("")
		|| password.trim().equalsIgnoreCase(""))
	    throw new SQLException(
		    "Required property either missing or configured incorrectly");

	int maxActiveVal = 4; // 100;
	int initialSize = 2; // 10;
	try {
	    maxActiveVal = (maxActive == null || maxActive.trim()
		    .equalsIgnoreCase("")) ? 10 : Integer.parseInt(maxActive);
	} catch (NumberFormatException e) {
	    logger.info("Defaulting the maxActive connections to "
		    + maxActiveVal);
	}
	int maxIdleVal = 1; // 10;
	int minIdleVal = 1; // 10;
	try {
	    maxIdleVal = (maxIdle == null || maxIdle.trim()
		    .equalsIgnoreCase("")) ? 10 : Integer.parseInt(maxIdle);
	} catch (NumberFormatException e) {
	    logger.info("Defaulting the maxIdleVal connections to "
		    + maxIdleVal);
	}
	long maxWaitVal = 3l * 1000l;
	try {
	    maxWaitVal = (maxWait == null || maxWait.trim()
		    .equalsIgnoreCase("")) ? 3l * 1000l : Long
		    .parseLong(maxWait) * 1000l;
	} catch (NumberFormatException e) {
	    logger.info("Defaulting the maxWaitVal connections to "
		    + maxWaitVal);
	}

	logger
		.info("Connection pooling properties maxActiveVal, maxActive, maxIdleVal, maxIdle, maxWaitVal, maxWait as");
	logger.info(" respectively are : " + maxActiveVal + "<" + maxActive
		+ ">, " + maxIdleVal + "<" + maxIdle + ">, " + maxWaitVal + "<"
		+ maxWait + ">");
	bds = new BasicDataSource();
	bds.setUsername(user);
	bds.setPassword(password);
	bds.setDriverClassName(driverName);
	bds.setUrl(url);
	bds.setInitialSize(initialSize);
	bds.setMaxActive(maxActiveVal);
	// bds.setMaxOpenPreparedStatements(80);
	bds.setMaxOpenPreparedStatements(1);
	bds.setPoolPreparedStatements(true);
	bds.setMinIdle(minIdleVal);
	bds.setMaxIdle(maxIdleVal);
	// bds.setMaxWait(10l*1000l);
	bds.setMaxWait(1000l);
	bds.setAccessToUnderlyingConnectionAllowed(true);
	// bds.setMinEvictableIdleTimeMillis(60000);
	bds.setMinEvictableIdleTimeMillis(1000);
	// bds.setTimeBetweenEvictionRunsMillis(10000);
	bds.setTimeBetweenEvictionRunsMillis(1000);
	bds.setTestWhileIdle(true);
	bds.setTestOnBorrow(true);
	bds.setValidationQuery(VALIDATION_QUERY);

	logger.info("Trying to connect to the DB with url as " + url);
	initialized = true;
	logger.info("Leaving the initializer method");

    }

    /**
     * cleaning up the connections that created by the BasicDataSource pool
     */
    public void shutdownDriver() {
	if (initialized) {
	    logger.info("shutting down the sql driver");
	    try {
		if (bds != null)
		    bds.close();
	    } catch (SQLException e) {
		logger
			.info(
				"SQL Exception while shutting down connection pool ",
				e);
	    }
	    logger.info("finished shutting down the sql driver");
	}
    }
}
