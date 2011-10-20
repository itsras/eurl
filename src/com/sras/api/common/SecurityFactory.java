package com.sras.api.common;

import com.sras.api.common.impl.SecurityManagerImpl;

public class SecurityFactory extends APIFactory {

    public SecurityFactory() {
	super();
    }

    public SecurityManager getManager() {
	return new SecurityManagerImpl();
    }
}
