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
import java.net.UnknownHostException;

import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchange.CompletedListener;
import com.github.khandroid.rest.RestExchangeFailedException;
import com.github.khandroid.rest.UnexpectedResponseException;


public class RestFunctionalityImpl implements RestFunctionality {
    private final HttpFunctionality mHttpFunc;

    
    public RestFunctionalityImpl(HttpFunctionality httpFunc) {
        if (httpFunc != null) {
            mHttpFunc = httpFunc;
        } else {
            throw new IllegalArgumentException("Passed parameter httpFunc is null");
        }
    }

    
    @Override
    public <T> String execute(RestExchange<T> x) throws RestExchangeFailedException {
        String ret = null;
        
        try {
            try {
                ret = x.perform(mHttpFunc);
            } catch (MalformedResponseException e) {
                throw new RestExchangeFailedException("Parsing response failed because of malformed response from server.", e);
            } catch (UnexpectedResponseException e) {
                throw new RestExchangeFailedException("Unexpected response code.", e);
            } 
        } catch (khandroid.ext.apache.http.client.ClientProtocolException e) {
            throw new RestExchangeFailedException("Executing request failed because of protocol violation.", e);
        } catch (UnknownHostException e) {
            throw new RestExchangeFailedException("Executing request failed. Unknown host. Is there internet connection?", e);
        } catch (IOException e) {
            throw new RestExchangeFailedException("Executing request failed.", e);
        }
        
        return ret;
    }


    @Override
    public <T> String execute(RestExchange<T> x, CompletedListener<T> listener) {
        String ret = null;
        
        try {
            try {
                ret = x.perform(mHttpFunc);
                if (x.isOk()) {
                    listener.onExchangeCompletedOk(x.getResult());
                } else {
                    listener.onExchangeCompletedFail(x.getResponse());    
                }
            } catch (MalformedResponseException e) {
                listener.onExchangeCompletedEpicFail(e);
            }
        } catch (Exception e) {
            listener.onExchangeCompletedEpicFail(e);
        }
        
        listener.onExchangeCompleted(x);
        
        return ret;
    }
    
    
    public void shutDown() {
        mHttpFunc.shutDown();
    }
}
