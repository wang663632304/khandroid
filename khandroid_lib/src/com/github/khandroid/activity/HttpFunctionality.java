package com.github.khandroid.activity;

import java.io.IOException;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;


public interface HttpFunctionality {
    public void setAutoShutdown(boolean value);
    public boolean isOnline();
    public String execute(HttpUriRequest httpRequest) throws ClientProtocolException, IOException;
    public void shutDown();
    
    public DefaultHttpClient getHttpClient();
}
