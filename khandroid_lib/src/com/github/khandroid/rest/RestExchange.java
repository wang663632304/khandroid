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

package com.github.khandroid.rest;

import java.io.IOException;

import com.github.khandroid.functionality.HttpFunctionality;
import com.github.khandroid.http.misc.KhandroidBasicResponseHandler;
import com.github.khandroid.misc.KhandroidLog;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;


abstract public class RestExchange<T> {
    private RestRequest mRequest;
    private boolean mExecuted = false;
    private RestResponse mResponse;
    private T mResult;
    private boolean mIsOk;


    abstract protected RestRequest createRequest();


    abstract protected T createResult(String source, int responseCode) throws MalformedResponseException, UnexpectedResponseException;


    public RestRequest getRequest() {
        if (mRequest == null) {
            mRequest = createRequest();
        }

        return mRequest;
    }


    /**
     * Executes {@link RestExchange}
     * 
     * 
     * @param httpClient
     * @return Raw response from the server (body). You can use this returned string for debugging purposes if exchange failed (e.g. to see what server returned that cannot be parsed) 
     * @throws ClientProtocolException
     * @throws IOException
     * @throws MalformedResponseException
     * @throws UnexpectedResponseException
     */
    public String perform(HttpFunctionality mHttpFunc) throws ClientProtocolException,
                                              IOException,
                                              MalformedResponseException,
                                              UnexpectedResponseException {
        
        ResponseHandler<String> responseHandler = new KhandroidBasicResponseHandler();
        HttpUriRequest req = getRequest().createHttpRequest();
        KhandroidLog.d("Executing: " + req.getURI().toString());
        String rawResponse = mHttpFunc.execute(req, responseHandler);

        RestResponseParser rp = new RestResponseParser();
        mResponse = rp.parse(rawResponse);
        if (mResponse.getStatus() == RestResponse.RESPONSE_STATUS_OK) {
            KhandroidLog.d("Response is OK");
            mResult = createResult(mResponse.getPayload(), mResponse.getResponseCode());
            mIsOk = true;
        } else {
            KhandroidLog.d("Response is ERROR: " + mResponse.getStatus());
            mIsOk = false;
        }

        mExecuted = true;
        
        return rawResponse;
    }


    public boolean isOk() {
        if (mExecuted) {
            //			if (result != null) {
            //				return true;
            //			} else {
            //				return false;
            //			}
            return mIsOk;
        } else {
            throw new IllegalStateException("Exchange was not performed.");
        }
    }


    public int getErrorCode() {
        if (mExecuted) {
            return mResponse.getResponseCode();
        } else {
            throw new IllegalStateException("Exchange was not performed.");
        }
    }


    public String getErrorMessage() {
        if (mExecuted) {
            return mResponse.getPayload();
        } else {
            throw new IllegalStateException("Exchange was not performed.");
        }
    }


    public T getResult() {
        if (isOk()) {
            return mResult;
        } else {
            throw new IllegalStateException("Trying to get result from exchange that ended in error.");
        }
    }


    public RestResponse getResponse() {
        return mResponse;
    }

    /**
     * Callback interface used to provide info about RestExchange result
     * 
     * @author ogre
     * 
     * @param <T>
     */
    public interface CompletedListener<T> {
        /**
         * Called when exchange completes successfully.
         * 
         * @param x
         *            Result of type <T>
         */
        void onExchangeCompletedOk(T x);


        /**
         * Called if response json is parsed successfully but it contains error status code (i.e. status is
         * RestResponse.RESPONSE_STATUS_ERR)
         * 
         * @param response
         *            RestResponse object from which you can extract the error code via
         *            {@link RestResponse#getResponseCode()}.
         */
        void onExchangeCompletedFail(RestResponse response);


        /**
         * Called if fatal error occurred during execution of the request like host is not found,
         * response cannot be parsed or some other serios error happened.
         * 
         * @param e
         */
        void onExchangeCompletedEpicFail(Exception e);


        /**
         * Called when exchange has completed.
         * Please note that this method is called <b>after</b> all other callback methods of this interface.
         * Use this method if you are interested only if exchange has completed but don't care about the result.
         * 
         * @param x
         *            Original RestExchange object (populated with Result on success)
         */
        void onExchangeCompleted(RestExchange<T> x);
    }

    abstract public class SimpleCompletedListener implements CompletedListener<T> {
        abstract void onExchangeCompletedFail();


        @Override
        public void onExchangeCompletedFail(RestResponse response) {
            onExchangeCompletedFail();
        }


        @Override
        public void onExchangeCompletedEpicFail(Exception e) {
            onExchangeCompletedFail();
        }
    }
}
