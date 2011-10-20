package com.sras.client;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import com.sras.database.ConnectionPoolManager;

public class ControllerFilter extends StrutsPrepareAndExecuteFilter {

    @Override
    public void destroy() {
	// TODO Auto-generated method stub
	super.destroy();
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
	    FilterChain arg2) throws IOException, ServletException {
	// TODO Auto-generated method stub
	super.doFilter(arg0, arg1, arg2);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
	// TODO Auto-generated method stub
	super.init(config);
	loadConnectionParams(config);
    }

    @Override
    protected void postInit(Dispatcher dispatcher, FilterConfig filterConfig) {
	// TODO Auto-generated method stub
	super.postInit(dispatcher, filterConfig);
    }

    private void loadConnectionParams(FilterConfig config) {
	ConnectionPoolManager cpm = ConnectionPoolManager.getProdInstance();
	if (!cpm.isLoaded()) {
	    cpm.setUrl(config.getInitParameter("DataSource.url"));
	    cpm.setDriverName(config.getInitParameter("DataSource.driverName"));
	    cpm.setHost(config.getInitParameter("DataSource.host"));
	    cpm.setPort(config.getInitParameter("DataSource.port"));
	    cpm.setInstance(config.getInitParameter("DataSource.instance"));
	    cpm.setUser(config.getInitParameter("DataSource.user"));
	    cpm.setPassword(config.getInitParameter("DataSource.password"));
	    cpm.setLoaded(true);
	}
    }
}
