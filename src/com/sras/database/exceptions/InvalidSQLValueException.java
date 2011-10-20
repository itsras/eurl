package com.sras.database.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Jun 1, 2010
 * Time: 10:28:08 AM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class InvalidSQLValueException extends RuntimeException
{
	/**
	 * Create a new exception.
	 * @param msg
	 */
    public InvalidSQLValueException(String msg)
    {
        super(msg);
    }
}
