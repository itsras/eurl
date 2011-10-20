package com.sras.client.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.sras.api.common.UserData;
import com.sras.api.shorturl.SUFactory;
import com.sras.api.shorturl.SUManager;
import com.sras.api.shorturl.UrlData;
import com.sras.api.shorturl.impl.UrlDataImpl;
import com.sras.client.utils.LoginUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;

/**
 * Created by IntelliJ IDEA. User: srinivk Date: Jan 27, 2011 Time: 2:23:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultAction extends ActionSupport implements
	ServletRequestAware, ServletResponseAware, Parameterizable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5090706760036943843L;
    private static Logger logger = Logger.getLogger(DefaultAction.class
	    .getName());
    private Map<String, String> params = new HashMap<String, String>();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public String execute() throws Exception {
	String reqUrl = request.getRequestURI().toString();
	String hashKey = reqUrl.substring(reqUrl.lastIndexOf("/") + 1);
	UserData user = LoginUtils.getUser(request, response);
	SUFactory suFactory = new SUFactory(user);
	SUManager suManager = suFactory.getManager();
	UrlData longUrl = suManager.read(hashKey);
	if (longUrl != null) {
	    request.setAttribute("url", longUrl.getLongUrl());
	    return "redirect";
	} else {
	    return "error";
	}
    }

    public String encrypt() throws Exception {
	logger.info("In Encrypt Method....");
	String urlStr = request.getParameter("url");

	if (urlStr.contains("\r\n")) {
	    String[] longUrls = urlStr.split("\r\n");
	    for (int i = 0; i < longUrls.length; i++) {
		encryptUrl(longUrls[i]);
	    }
	} else {
	    return encryptUrl(urlStr);
	}
	return "success";
    }

    private String encryptUrl(String urlStr) {
	UserData user = LoginUtils.getUser(request, response);
	SUFactory suFactory = new SUFactory(user);
	SUManager suManager = suFactory.getManager();
	UrlData ud = new UrlDataImpl();
	ud.setLongUrl(urlStr);
	ud.setPrivacy(false);
	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar
		.getInstance().getTime().getTime());
	ud.setCreated(currentTimestamp);
	ud.setUserId(1); // TODO: This has to be removed after impl'ing login
			 // module.
	boolean isSaved = suManager.save(ud);
	// addActionMessage("encrypted url");
	if (isSaved) {
	    return "success";
	} else {
	    return "error";
	}
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
	this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
	this.response = response;

    }

    @Override
    public void addParam(String key, String value) {
	params.put(key, value);
    }

    @Override
    public Map<String, String> getParams() {
	// TODO Auto-generated method stub
	return this.params;
    }

    @Override
    public void setParams(Map<String, String> params) {
	this.params = params;
    }
}