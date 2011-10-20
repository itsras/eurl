package com.sras.client.utils;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class HelperUtilities {
    private static Logger logger = Logger.getLogger(HelperUtilities.class
	    .getName());

    public static InetSocketAddress getProxySettings() {
	try {
	    System.setProperty("java.net.useSystemProxies", "true");
	    List<Proxy> proxyList = ProxySelector.getDefault().select(
		    new URI("http://www.google.com/"));
	    if (proxyList != null) {
		Iterator<Proxy> proxyListIter = proxyList.iterator();
		logger.info("Proxies count : " + proxyList.size());
		while (proxyListIter.hasNext()) {
		    Proxy proxy = (Proxy) proxyListIter.next();
		    logger.info("proxy hostname :: " + proxy.type());
		    logger.info("proxy address :: " + proxy.address());
		    InetSocketAddress addr = (InetSocketAddress) proxy
			    .address();
		    return addr;
		}
	    }
	} catch (Exception e) {
	    logger.info("Exception while getting proxy settings :: " + e);
	}
	return null;
    }

    public static String escapeChars(Object s) {
	// Convert problem characters to JavaScript Escaped values
	if (s == null) {
	    return "";
	}

	String t = (String) s;
	t = replace(t, "\\", "\\\\"); // replace backslash with \\
	t = replace(t, "'", "\\\'"); // replace an single quote with \'
	t = replace(t, "\"", "\\\""); // replace a double quote with \"
	t = replace(t, "\r", "\\r"); // replace CR with \r;
	t = replace(t, "\n", "\\n"); // replace LF with \n;

	return t;
    }

    static String replace(String s, String one, String another) {
	// In a string replace one substring with another
	if (s.equals(""))
	    return "";
	String res = "";
	int i = s.indexOf(one, 0);
	int lastpos = 0;
	while (i != -1) {
	    res += s.substring(lastpos, i) + another;
	    lastpos = i + one.length();
	    i = s.indexOf(one, lastpos);
	}
	res += s.substring(lastpos); // the rest
	return res;
    }

    @SuppressWarnings("deprecation")
    public static String getTitle(String longUrl) throws Exception {
	String title = "";
	try {
	    URL url = new URL(longUrl);
	    URLConnection urlConnection = url.openConnection();
	    DataInputStream dis = new DataInputStream(urlConnection
		    .getInputStream());
	    String html = "", tmp = "";
	    int readLineCount = 0;
	    while ((tmp = dis.readLine()) != null) {
		html += " " + tmp;
		readLineCount++;
		if (readLineCount == 50) {
		    break;
		}
	    }
	    dis.close();

	    html = html.replaceAll("\\s+", " ");
	    Pattern p = Pattern.compile("<title>(.*?)</title>");
	    Matcher m = p.matcher(html);
	    while (m.find() == true) {
		title = m.group(1).trim();
	    }
	    return title;
	} catch (Exception e) {
	    return "";
	}
    }
}
