package com.sras.client.utils;

public class BaseConverterUtils {

    private static final String baseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String toBase62(long decimalNumber) { 
	return fromDecimalToOtherBase(62, decimalNumber);
    }

    public static String toBase36(long decimalNumber) {
	return fromDecimalToOtherBase(36, decimalNumber);
    }

    public static String toBase16(long decimalNumber) {
	return fromDecimalToOtherBase(16, decimalNumber);
    }

    public static String toBase8(long decimalNumber) {
	return fromDecimalToOtherBase(8, decimalNumber);
    }

    public static String toBase2(long decimalNumber) {
	return fromDecimalToOtherBase(2, decimalNumber);
    }

    public static long fromBase62(String base62Number) {
	return fromOtherBaseToDecimal(62, base62Number);
    }

    public static long fromBase36(String base36Number) {
	return fromOtherBaseToDecimal(36, base36Number);
    }

    public static long fromBase16(String base16Number) {
	return fromOtherBaseToDecimal(16, base16Number);
    }

    public static long fromBase8(String base8Number) {
	return fromOtherBaseToDecimal(8, base8Number);
    }

    public static long fromBase2(String base2Number) {
	return fromOtherBaseToDecimal(2, base2Number);
    }

    private static String fromDecimalToOtherBase(long base, long decimalNumber) {
	String tempVal = decimalNumber == 0 ? "0" : "";
	long mod = 0;

	while (decimalNumber != 0) {
	    mod = decimalNumber % base;
	    tempVal = baseDigits.substring((int) mod, (int) mod + 1) + tempVal;
	    decimalNumber = decimalNumber / base;
	}

	return tempVal;
    }

    private static long fromOtherBaseToDecimal(long base, String number) {
	int iterator = number.length();
	long returnValue = 0;
	long multiplier = 1;

	while (iterator > 0) {
	    returnValue = returnValue
		    + (baseDigits.indexOf(number.substring(iterator - 1,
			    iterator)) * multiplier);
	    multiplier = multiplier * base;
	    --iterator;
	}
	return returnValue;
    }

}
