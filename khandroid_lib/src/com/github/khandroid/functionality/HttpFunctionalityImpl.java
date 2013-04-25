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

package com.github.khandroid.functionality;

import java.io.IOException;

import com.github.khandroid.http.KhandroidBasicResponseHandler;

import khandroid.ext.apache.http.HttpResponse;
import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;


public class HttpFunctionalityImpl implements HttpFunctionality {
    private final HttpClient mHttpClient;
    private boolean mIsShutdown = false;


    public HttpFunctionalityImpl(HttpClient httpClient) {
        if (httpClient != null) {
            mHttpClient = httpClient;
        } else {
            throw new IllegalArgumentException("Passed httpClient is null");
        }
    }
    

    public String execute(HttpUriRequest httpRequest) throws ClientProtocolException, IOException {
        ResponseHandler<String> responseHandler = new KhandroidBasicResponseHandler();

        HttpClient httpClient = getHttpClient();
        String ret = httpClient.execute(httpRequest, responseHandler);

        return ret;
    }


    public HttpResponse executeForHttpResponse(HttpUriRequest httpRequest) throws ClientProtocolException,
                                                                          IOException {
        HttpClient httpClient = getHttpClient();
        return httpClient.execute(httpRequest);
    }


    public void shutDown() {
        shutDownInBackground();
    }


    public boolean isShutdown() {
        return mIsShutdown;
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


    protected HttpClient getHttpClient() {
        return mHttpClient;
    }
}
