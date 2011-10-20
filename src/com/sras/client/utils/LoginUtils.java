package com.sras.client.utils;

import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sras.api.common.SecurityFactory;
import com.sras.api.common.SecurityManager;
import com.sras.api.common.UserData;
import com.sras.api.common.impl.UserDataImpl;

public class LoginUtils {

    final static String USER_HANDLE = "loggedInUser";
    final static String LOGIN_ATTEMPT = "loginAttempt";
    final static String COOKIE_NAME = "user_id";

    public static UserData getUser(HttpServletRequest req,
	    HttpServletResponse res) {
	UserData user = (UserData) req.getSession().getAttribute(USER_HANDLE);
	if (user == null) {
	    Cookie cookies[] = req.getCookies();
	    Cookie myCookie = null;
	    if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
		    if (cookies[i].getName().equals(COOKIE_NAME)) {
			myCookie = cookies[i];
			break;
		    }
		}
	    }

	    SecurityFactory cmFactory = new SecurityFactory();
	    SecurityManager manager = cmFactory.getManager();
	    long userId = 0;
	    if (myCookie == null) {
		userId = createUser(manager);
	    } else {
		userId = Long.parseLong(myCookie.getValue());
		user = manager.readUser(userId);
		if (user == null) {
		    userId = createUser(manager);
		}
	    }
	    user = manager.readUser(userId);
	    Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(userId));
	    cookie.setSecure(true);
	    cookie.setVersion(0);
	    cookie.setMaxAge(365 * 24 * 60 * 60);
	    res.addCookie(cookie);
	    req.getSession().setAttribute(USER_HANDLE, user);
	}
	return user;
    }

    public static long createUser(SecurityManager manager) {
	UserData userData = null;
	Calendar cal = Calendar.getInstance();
	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(cal
		.getTime().getTime());
	userData = new UserDataImpl();
	userData.setUserName("anonymous" + System.currentTimeMillis());
	userData.setPassWord("dummy");
	userData.setEmail("dummy@dummy.com");
	userData.setMobileNumber("9999999999");
	userData.setCreated(currentTimestamp);
	return manager.createUser(userData);
    }
}
