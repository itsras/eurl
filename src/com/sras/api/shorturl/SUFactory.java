package com.sras.api.shorturl;

import com.sras.api.common.APIFactory;
import com.sras.api.common.UserData;
import com.sras.api.shorturl.impl.SUManagerImpl;

final public class SUFactory extends APIFactory{
    public SUFactory(UserData userData) {
	super(userData);
    }

    public SUManager getManager() {
	return new SUManagerImpl(userData);
    }
}
