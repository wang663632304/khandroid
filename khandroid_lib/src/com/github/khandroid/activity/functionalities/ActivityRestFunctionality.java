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

package com.github.khandroid.activity.functionalities;

import java.io.IOException;
import java.net.UnknownHostException;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchangeFailedException;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;


abstract public class ActivityRestFunctionality extends ActivityHttpFunctionality {

    public ActivityRestFunctionality(HostActivity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
    }


    public void executeExchange(RestExchange x) throws RestExchangeFailedException {
        try {
            DefaultHttpClient httpClient = getHttpClient();
            //          String rawResponse = httpClient.execute(x.getRequest().createHttpRequest(), responseHandler);

            try {
                x.perform(httpClient);
            } catch (MalformedResponseException e) {
                throw new RestExchangeFailedException("Parsing response failed because of malformed response from server.", e);
            }
        } catch (ClientProtocolException e) {
            throw new RestExchangeFailedException("Executing request failed because of protocol violation.", e);
        } catch (UnknownHostException e) {
            throw new RestExchangeFailedException("Executing request failed. Unknown host. Is there internet connection?", e);
        } catch (IOException e) {
            throw new RestExchangeFailedException("Executing request failed.", e);
        }
    }


    @Override
    abstract protected DefaultHttpClient createHttpClient();


    @Override
    public DefaultHttpClient getHttpClient() {
        return (DefaultHttpClient) super.getHttpClient();
    }
}
