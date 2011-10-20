package com.sras.client.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.sras.client.utils.LoginUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor implements
	Interceptor, StrutsStatics {
    private static Logger logger = Logger.getLogger(LoginInterceptor.class
	    .getName());

    public void init() {
	logger.info("Intializing LoginInterceptor");
    }

    public void destroy() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
	final ActionContext context = invocation.getInvocationContext();
	HttpServletRequest request = (HttpServletRequest) context
		.get(HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) context
		.get(HTTP_RESPONSE);
	LoginUtils.getUser(request, response);
	return invocation.invoke();
    }
}
