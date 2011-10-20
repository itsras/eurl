package com.sras.client.action;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.sras.client.utils.HelperUtilities;
import com.opensymphony.xwork2.ActionSupport;

public class RedirectAction extends ActionSupport implements
	ServletRequestAware, ServletResponseAware {

    private static final long serialVersionUID = -3413094188535674205L;
    private static Logger logger = Logger.getLogger(RedirectAction.class
	    .getName());

    private HttpServletRequest request;
    private HttpServletResponse response;

    public String execute() throws Exception {
	@SuppressWarnings("unused")
	String eurl = request.getParameter("eurl");
	String urlStr = "";
//	if (eurl != null) {
//	    urlStr = HelperUtilities.read(eurl);
//	}
//	if (urlStr == null || urlStr.trim().length() == 0) {
//	    return "error";
//	}
	urlStr = (urlStr != null && !urlStr.startsWith("http://")) ? "http://"
		+ urlStr : urlStr;
	System.out.println("URL STRING :: " + urlStr);

	InetSocketAddress address = HelperUtilities.getProxySettings();
	if (address != null) {
	    logger.info("http.proxyHost :: " + address.getHostName());
	    logger.info("http.proxyPort :: "
		    + String.valueOf(address.getPort()));

	    System.setProperty("http.proxyHost", address.getHostName());
	    System.setProperty("http.proxyPort", String.valueOf(address
		    .getPort()));
	}

	int ByteRead, ByteWritten = 0;
	URL url = new URL(urlStr);
	URLConnection connection = url.openConnection();
	connection.connect();
	InputStream is = connection.getInputStream();

	logger.info("URL is " + url.toString());
	logger.info("protocol is " + url.getProtocol());
	logger.info("authority is " + url.getAuthority());
	logger.info("file name is " + url.getFile());
	logger.info("host is " + url.getHost());
	logger.info("path is " + url.getPath());
	logger.info("port is " + url.getPort());
	logger.info("default port is " + url.getDefaultPort());
	logger.info("query is " + url.getQuery());
	logger.info("ref is " + url.getRef());

	String file = connection.getURL().getFile();
	file = (file != null && file.contains("\\")) ? file.substring(file
		.lastIndexOf("\\")) : file;
	file = (file != null && file.contains("/")) ? file.substring(file
		.lastIndexOf("/")) : file;

	if (file != null) {
	    logger.info("Content Type is " + connection.getContentType());
	    logger.info("Content Length is " + connection.getContentLength());
	    logger.info("File Name is " + file);

	    response.setContentType(connection.getContentType());
	    response.setContentLength((int) connection.getContentLength());
	    response.setHeader("Content-Disposition", "attachment; filename=\""
		    + file + "\"");
	    Map<String, List<String>> map = connection.getHeaderFields();
	    Iterator<String> iter = map.keySet().iterator();
	    while (iter.hasNext()) {
		String key = iter.next();
		if (key != null) {
		    List<String> value = map.get(key);
		    if (value != null) {
			for (int i = 0; i < value.size(); i++) {
			    if (value.get(i) != null) {
				response.setHeader(key, value.get(i));
				logger.info("Header Field Key is " + key
					+ " Value is " + value.get(i));
				break;
			    }
			}
		    }
		}
	    }
	    byte[] buf = new byte[1024];
	    ServletOutputStream sOut = response.getOutputStream();
	    while ((ByteRead = is.read(buf)) != -1) {
		sOut.write(buf, 0, ByteRead);
		ByteWritten += ByteRead;
	    }
	}
	return "success";
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
	this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
	this.response = response;
    }

}
