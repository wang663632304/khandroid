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

package com.github.khandroid.http;

import java.io.IOException;

import khandroid.ext.apache.http.HttpResponse;
import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;

import com.github.khandroid.core.FragmentAttachedFunctionality;
import com.github.khandroid.core.HostFragment;
import com.github.khandroid.functionality.HttpFunctionality;
import com.github.khandroid.functionality.HttpFunctionalityImpl;


public class FragmentHttpFunctionality extends FragmentAttachedFunctionality implements
        HttpFunctionality {

    private final HttpFunctionality mHttpFunc;
    private boolean mAutoShutdown = true;


    public FragmentHttpFunctionality(HostFragment fragment, HttpClient httpClient) {
        super(fragment);
        
        if (httpClient != null) {
            mHttpFunc = new HttpFunctionalityImpl(httpClient);
        } else {
            throw new IllegalArgumentException("Parameter httpClient is null");
        }
    }


    public boolean setAutoShutdown(boolean value) {
        boolean ret = mAutoShutdown;
        mAutoShutdown = value;
        return ret;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAutoShutdown) {
            mHttpFunc.shutDown();
        }
    }


    @Override
    public String execute(HttpUriRequest httpRequest) throws ClientProtocolException, IOException {
        setAutoShutdown(false);
        String ret = mHttpFunc.execute(httpRequest);
        setAutoShutdown(true);

        return ret;
    }


    @Override
    public HttpResponse executeForHttpResponse(HttpUriRequest httpRequest) throws ClientProtocolException,
                                                                          IOException {
        setAutoShutdown(false);
        HttpResponse ret = mHttpFunc.executeForHttpResponse(httpRequest);
        setAutoShutdown(true);

        return ret;
    }


    @Override
    public void shutDown() {
        mHttpFunc.shutDown();
    }

    
    @Override
    public <T> T execute(HttpUriRequest httpRequest, ResponseHandler<T> responseHandler) throws ClientProtocolException,
                                                                                        IOException {
        setAutoShutdown(false);
        T ret =  mHttpFunc.execute(httpRequest, responseHandler);
        setAutoShutdown(true);
        
        return ret;
    }
}
