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

import com.github.khandroid.functionality.RestFunctionality;
import com.github.khandroid.kat.KhandroidAsyncTask;


/**
 * Used to execute {@link RestExchange}s
 * Difference between this and the regular {@link KhandroidAsyncTask} is that RestAsyncTask wraps the resulting object
 * with {@link ResultWrapper} in order to provide you with optional status code which you may use to distinguish between
 * different error situations (when RestExchange failed).
 * 
 * @author ogre
 * 
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
abstract public class RestAsyncTask<Params, Progress, Result> extends
        KhandroidAsyncTask<Params, Progress, RestAsyncTask.ResultWrapper<Result>> {
    
    private RestFunctionality mRestFunc;
    
    abstract protected RestExchange<Result> createRestExchange();
    
    
    public RestAsyncTask(RestFunctionality restFunc) {
        super();
        
        if (restFunc != null) {
            mRestFunc = restFunc;
        } else {
            throw new IllegalStateException("Parameter restFunc is null");
        }
    }


    @Override
    protected ResultWrapper<Result> doInBackground(Params... params) {
        ResultWrapper<Result> ret;

        RestExchange<Result> x = createRestExchange();
        try {
            mRestFunc.execute(x);
            if (x.isOk()) {
                ret = new ResultWrapper<Result>(ResultWrapper.STATUS_OK, x.getResult());
            } else {
                ret = new ResultWrapper<Result>(ResultWrapper.STATUS_SOFT_ERROR, null);
            }
        } catch (RestExchangeFailedException e) {
            ret = new ResultWrapper<Result>(ResultWrapper.STATUS_HARD_ERROR, null);
        }

        return ret;
    }
    
    
    public static class ResultWrapper<Result> {
        /**
         * You may use STATUS_OK to indicate that exchange completed successfully. {@see #getStatus}
         */
        public static final int STATUS_OK = 0;

        /**
         * You may use STATUS_SOFT_ERROR to indicate that JSON response was parsed successfully
         * but contained error code (i.e. {@link RestResponse} object had status
         * {@link RestResponse#RESPONSE_STATUS_ERR}). Usually this code is used when there is a chance to recover
         * from this error like to execute second request with different parameters (depending
         * on your/server's logic). {@see #getStatus} Example for "soft" error: you may have "login" RestExchange that
         * sends to the server username and password.
         * Server may accept the request but return error because username/password combo is invalid. In such case you
         * may present the user with dialog that states "Invalid username or password" and when the user enters new
         * credentials to try the exchange again with the new data.
         */
        public static final int STATUS_SOFT_ERROR = -1;

        /**
         * You may use STATUS_HARD_ERROR to indicate that some serious/fatal error happened while
         * executing the RestExchange like "host not found" or "I/O error". Usually this code is
         * used when you can't recover from this error because it happened for reasons out of
         * your control. {@see #getStatus}
         */
        public static final int STATUS_HARD_ERROR = -2;

        private final int status;
        private final Result result;


        /**
         * Creates a ResultWrapper object that wraps your result object of type Result and optional status code.
         * 
         * @param status
         *            You may use your own values for the status field. Usually @see {@value ResultWrapper#STATUS_OK},
         *            {@value ResultWrapper#STATUS_SOFT_ERROR} or {@value ResultWrapper#STATUS_HARD_ERROR} are used.
         * @param result
         *            Your result object
         */
        public ResultWrapper(int status, Result result) {
            super();
            this.status = status;
            this.result = result;
        }


        /**
         * Return the status code
         * @return
         */
        public int getStatus() {
            return status;
        }


        /**
         * Returns the wrapped data, i.e. your result object
         * @return
         */
        public Result getData() {
            return result;
        }
    }
}
