/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.khandroid.fragment.functionalities;

import java.io.IOException;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.khandroid.fragment.FragmentUniqueAttachedFunctionality;
import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.functionality.HttpFunctionality;
import com.github.khandroid.http.KhandroidBasicResponseHandler;


abstract public class FragmentHttpFunctionality extends FragmentUniqueAttachedFunctionality implements HttpFunctionality {
    private DefaultHttpClient mHttpClient;
    private boolean mIsShutdown = false;
    private boolean mAutoShutdown = true;

    
    public FragmentHttpFunctionality(HostFragment fragment) {
        super(fragment);
    }

    
    public boolean setAutoShutdown(boolean value) {
        boolean ret = mAutoShutdown;
        mAutoShutdown = value;
        return ret;
    } 
    
    
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getFragment().getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        return (mHttpClient != null) ? true : false;
    }
    
    
    @Override
    public void onStop() {
        super.onStop();
        if (mHttpClient != null) {
            if (mAutoShutdown) {
                shutDownInBackground();
                mIsShutdown = true;
            }
        }
    }


    public void shutDown() {
        if (mHttpClient != null && !mIsShutdown) {
            shutDownInBackground();
            mIsShutdown = true;
        }
    }

    
    // On Honeycomb+ NetworkOnMainThreadException exception is thrown if you try to shutdown on UI thread
    private void shutDownInBackground() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpClient.getConnectionManager().shutdown();
            }
        });
        
        t.start();
    }
    
    
    public boolean isShutdown() {
        return mIsShutdown;
    }
    

    abstract protected HttpClient createHttpClient();
    
    
    public DefaultHttpClient getHttpClient() {
        if (mHttpClient == null || mIsShutdown) {
            mHttpClient = (DefaultHttpClient) createHttpClient();
        }

        return mHttpClient;
    }
}
