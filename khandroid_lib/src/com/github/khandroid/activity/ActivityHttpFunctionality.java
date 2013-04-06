package com.github.khandroid.activity;


import java.io.IOException;

import com.github.khandroid.http.KhandroidBasicResponseHandler;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


abstract public class ActivityHttpFunctionality extends ActivityUniqueAttachedFunctionality implements HttpFunctionality {
	private DefaultHttpClient httpClient;
	private boolean isShutdown = false;
	private boolean autoShutdown = true;

	
	public ActivityHttpFunctionality(HostActivity activity) {
		super(activity);
	}

	
	public void setAutoShutdown(boolean value) {
		autoShutdown = value;
	}
	
	
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		
		return false;
	}
	
	
	public String execute(HttpUriRequest httpRequest) throws ClientProtocolException, IOException {
		ResponseHandler<String> responseHandler = new KhandroidBasicResponseHandler();
		
		HttpClient httpClient = getHttpClient();
		String ret = httpClient.execute(httpRequest, responseHandler);
			
		return ret;			
	}
	


	protected boolean isHttpClientInitialized() {
		return (httpClient != null) ? true : false;
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		if (httpClient != null) {
			if (autoShutdown) {
			    shutDownInBackground();
				isShutdown = true;
			}
		}
	}


	public void shutDown() {
		if (httpClient != null && !isShutdown) {
		    shutDownInBackground();
			isShutdown = true;
		}
	}

	
	// On honeycomb NetworkOnMainThreadException exception is thrown if you try to shutdown on UI thread
	private void shutDownInBackground() {
	    Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                httpClient.getConnectionManager().shutdown();
            }
        });
	    
	    t.start();
	}
	
	
	public boolean isShutdown() {
		return isShutdown;
	}
	

	abstract protected HttpClient createHttpClient();
	
	
	public DefaultHttpClient getHttpClient() {
		if (httpClient == null || isShutdown) {
			httpClient = (DefaultHttpClient) createHttpClient();
		}

		return httpClient;
	}
}
