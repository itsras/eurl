package com.sras.api.common;

public abstract class APIFactory {

    protected UserData userData;

    public APIFactory() {
	// Used to crate a manager which creates users for the application.
    }

    public APIFactory(UserData userData) {
	this.userData = userData;
    }
}
