package com.sras.database;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA. User: admin Date: Jun 1, 2010 Time: 10:27:07 AM To
 * change this template use File | Settings | File Templates.
 */
public class SQLDataType {

    public static SQLDataType Boolean(Boolean value) {
	return new SQLDataType(value, Types.BIT);
    }

    public static SQLDataType Boolean(boolean value) {
	return new SQLDataType(Boolean.valueOf(value), Types.BIT);
    }

    public static SQLDataType Integer(Integer value) {
	return new SQLDataType(value, Types.INTEGER);
    }

    public static SQLDataType Integer(int value) {
	return new SQLDataType(Integer.valueOf(value), Types.INTEGER);
    }

    public static SQLDataType Short(Short value) {
	return new SQLDataType(value, Types.INTEGER);
    }

    public static SQLDataType Short(short value) {
	return new SQLDataType(Integer.valueOf(value), Types.INTEGER);
    }

    public static SQLDataType String(String value) {
	return new SQLDataType(value, Types.VARCHAR);
    }

    /**
     * Ensure that the String is no longer than maxBytes. Java strings can have
     * unicode characters. the maximum string length in Oracle is 4000 bytes. If
     * more than 4000 bytes is written to a VARCHAR2(4000 CHAR) it throws
     * ORA-01461: can bind a LONG value only for insert into LONG column. google
     * ORA-01461 unicode
     * 
     * @author deackoff
     * @see http://java.sun.com/mailers/techtips/corejava/2006/tt0822.html
     */
    public static SQLDataType String(String value, int maxBytes) {
	try {
	    byte[] valueBytes = value.getBytes("UTF-8");
	    int valueBytesLength = valueBytes.length;
	    if (valueBytesLength <= maxBytes) {
		return new SQLDataType(value, Types.VARCHAR);
	    }
	    int newLength = Math.min(value.length(), maxBytes);
	    String newValue = value.substring(0, newLength);
	    while (true) {
		valueBytes = newValue.getBytes("UTF-8");
		valueBytesLength = valueBytes.length;
		if (valueBytesLength <= maxBytes) {
		    return new SQLDataType(newValue, Types.VARCHAR);
		}
		newLength = newLength
			- Math.max(1, ((valueBytesLength - maxBytes) / 2));
		newValue = newValue.substring(0, newLength);
	    }
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    public static SQLDataType String(Collection<?> value) {
	return new SQLDataType(
		value != null ? value.toString() : (String) null, Types.VARCHAR);
    }

    public static SQLDataType Long(Long value) {
	return new SQLDataType(value, Types.DOUBLE);
    }

    public static SQLDataType Long(long value) {
	return new SQLDataType(Long.valueOf(value), Types.DOUBLE);
    }

    public static SQLDataType Float(Float value) {
	return new SQLDataType(value, Types.REAL);
    }

    public static SQLDataType Float(float value) {
	return new SQLDataType(new Float(value), Types.REAL);
    }

    public static SQLDataType Double(Double value) {
	return new SQLDataType(value, Types.DOUBLE);
    }

    public static SQLDataType Double(double value) {
	return new SQLDataType(new Double(value), Types.DOUBLE);
    }

    public static SQLDataType BigDecimal(BigDecimal value) {
	return new SQLDataType(value, Types.DECIMAL);
    }

    public static SQLDataType BinaryStream(byte[] value) {
	return new SQLDataType(new ByteArrayInputStream(value), Types.BINARY);
    }

    public static SQLDataType BinaryStream(String value) {
	return SQLDataType.BinaryStream(value.getBytes());
    }

    public SQLDataType BinaryStream(InputStream value) {
	return new SQLDataType(value, Types.BINARY);
    }

    public static SQLDataType Raw(byte[] value) {
	return new SQLDataType(value, Types.BINARY);
    }

    public static SQLDataType Raw(String value) {
	if (value == null) {
	    return new SQLDataType(null, Types.BINARY);
	}

	try {
	    return new SQLDataType(value.getBytes("UTF-8"), Types.BINARY);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    public static SQLDataType Date(Date value) {
	return new SQLDataType(value, Types.DATE);
    }

    public static SQLDataType Time(Time value) {
	return new SQLDataType(value, Types.TIME);
    }

    public static SQLDataType Timestamp(Timestamp value) {
	return new SQLDataType(value, Types.TIMESTAMP);
    }

    public static SQLDataType Timestamp(java.util.Date value) {
	return new SQLDataType(value != null ? new Timestamp(value.getTime())
		: null, Types.TIMESTAMP);
    }

    public static SQLDataType Clob(Clob value) {
	return new SQLDataType(value, Types.CLOB);
    }

    public static SQLDataType Blob(Blob value) {
	return new SQLDataType(value, Types.BLOB);
    }

    public static SQLDataType Array(Array value) {
	return new SQLDataType(value, Types.ARRAY);
    }

    public static SQLDataType SQLData(SQLData value) {
	return new SQLDataType(value, Types.STRUCT);
    }

    public static SQLDataType Ref(Ref value) {
	return new SQLDataType(value, Types.REF);
    }

    public static SQLDataType Object(Object value, int type) {
	if (value instanceof java.util.Date) {
	    return new SQLDataType(new Timestamp(((java.util.Date) value)
		    .getTime()), Types.TIMESTAMP);
	}

	return new SQLDataType(value, type);
    }

    /*
     * Various static methods that will construct a primitive type wrapper or
     * return null if the result is really null. JDBC has a braindead interface
     * that requires you to get the result first and then check for null by
     * calling wasNull() afterwards. Duh.
     */
    public static String String(ResultSet rs, int colIndex) throws SQLException {
	return (rs.getString(colIndex));
    }

    public static Boolean Boolean(ResultSet rs, int colIndex)
	    throws SQLException {
	boolean d = rs.getBoolean(colIndex);
	return rs.wasNull() ? null : Boolean.valueOf(d);
    }

    public static Blob Blob(ResultSet rs, int colIndex) throws SQLException {
	Blob b = rs.getBlob(colIndex);
	return rs.wasNull() ? null : b;
    }

    public static Integer Integer(ResultSet rs, int colIndex)
	    throws SQLException {
	int d = rs.getInt(colIndex);
	return rs.wasNull() ? null : Integer.valueOf(d);
    }

    public static Short Short(ResultSet rs, int colIndex) throws SQLException {
	short d = rs.getShort(colIndex);
	return rs.wasNull() ? null : Short.valueOf(d);
    }

    public static Long Long(ResultSet rs, int colIndex) throws SQLException {
	long d = rs.getLong(colIndex);
	return rs.wasNull() ? null : Long.valueOf(d);
    }

    public static Float Float(ResultSet rs, int colIndex) throws SQLException {
	float d = rs.getFloat(colIndex);
	return rs.wasNull() ? null : Float.valueOf(d);
    }

    public static Double Double(ResultSet rs, int colIndex) throws SQLException {
	double d = rs.getDouble(colIndex);
	return rs.wasNull() ? null : Double.valueOf(d);
    }

    public static byte[] ByteArray(ResultSet rs, int colIndex)
	    throws SQLException {
	InputStream is = rs.getBinaryStream(colIndex);
	if (is == null) {
	    return null;
	}

	String str = is.toString();
	return str.getBytes();
    }

    public static byte[] Raw(ResultSet rs, int colIndex) throws SQLException {
	byte[] result = rs.getBytes(colIndex);
	return rs.wasNull() ? null : result;
    }

    public static Date Date(ResultSet rs, int colIndex) throws SQLException {
	Timestamp d = rs.getTimestamp(colIndex);
	return rs.wasNull() ? null : new Date(d.getTime());
    }

    public static Timestamp Timestamp(ResultSet rs, int colIndex)
	    throws SQLException {
	Timestamp t = rs.getTimestamp(colIndex);
	return rs.wasNull() ? null : t;
    }

    public static InputStream BinaryStream(ResultSet rs, int colIndex)
	    throws SQLException {
	return rs.getBinaryStream(colIndex);
    }

    public static String BinaryStreamString(ResultSet rs, int colIndex)
	    throws SQLException, IOException {
	InputStream is = rs.getBinaryStream(colIndex);
	StringWriter sw = new StringWriter();
	int chunk;

	while ((chunk = is.read()) != -1) {
	    sw.write(chunk);
	}
	sw.flush();

	return sw.getBuffer().toString();
    }

    public static String StringFromRaw(ResultSet rs, int colIndex)
	    throws SQLException {
	byte[] bytes = ByteArray(rs, colIndex);
	if (bytes == null) {
	    return null;
	}
	try {
	    return new String(bytes, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    public static String StringFromCLOB(ResultSet rs, int colIndex)
	    throws SQLException {
	String s = null;
	InputStream is = rs.getBinaryStream(colIndex);

	if (is != null) {
	    s = is.toString();
	}
	return s;
    }

    public static Collection<String> Collection(ResultSet rs, int colIndex)
	    throws SQLException {
	String str = rs.getString(colIndex);
	if (str == null) {
	    return null;
	}
	ArrayList<String> list = new ArrayList<String>();
	StringTokenizer cst = new StringTokenizer(str.substring(1,
		str.length() - 1), ",");
	while (cst.hasMoreTokens()) {
	    list.add(cst.nextToken().trim());
	}
	return list;

    }

    // accessors
    public int getType() {
	return type;
    }

    public Object getValue() {
	return value;
    }

    public boolean isNull() {
	return value == null;
    }

    public String toString() {
	return value != null ? value.toString() : NULL;
    }

    // private methods
    private SQLDataType(Object value, int type) {
	this.value = value;
	this.type = type;
    }

    private static final String NULL = "null";

    // member data
    private Object value;
    private int type;
}
