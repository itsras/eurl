package com.sras.api.common;

public interface SecurityManager {

    public long createUser(UserData userData);

    public UserData readUser(long id);

}
