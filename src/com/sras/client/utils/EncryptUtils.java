package com.sras.client.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtils {
    private static Logger logger = Logger.getLogger(EncryptUtils.class
	    .getName());

    public static final String key = "key phrase used for XOR-ing";
    public static final String DEFAULT_ENCODING = "UTF-8";

    static BASE64Encoder enc;
    static BASE64Decoder dec;

    static {
	enc = new BASE64Encoder();
	dec = new BASE64Decoder();
    }

    public static String base64encode(String text) {
	if (text == null) {
	    return null;
	}

	try {
	    String rez = enc.encode(text.getBytes(DEFAULT_ENCODING));
	    return rez;
	} catch (UnsupportedEncodingException e) {
	    logger.info("Exception while encrypting URL :: " + e);
	    return null;
	}
    }// base64encode

    public static String base64decode(String text) {
	if (text == null) {
	    return null;
	}

	try {
	    return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
	} catch (IOException e) {
	    logger.info("Exception while decrypting URL :: " + e);
	    return null;
	}

    }// base64decode

    public static String xorMessage(String message) {
	try {
	    if (message == null) {
		return null;
	    }

	    char[] keys = key.toCharArray();
	    char[] mesg = message.toCharArray();

	    int ml = mesg.length;
	    int kl = keys.length;
	    char[] newmsg = new char[ml];

	    for (int i = 0; i < ml; i++) {
		newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
	    }// for i
	    mesg = null;
	    keys = null;
	    return new String(newmsg);
	} catch (Exception e) {
	    logger.info("Exception while xor operation :: " + e);
	    return null;
	}
    }// xorMessage
}// class