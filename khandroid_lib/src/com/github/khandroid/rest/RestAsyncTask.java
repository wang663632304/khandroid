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
 * with {@link RestResult} in order to provide you with optional status code which you may use to distinguish between
 * different error situations (when RestExchange failed).
 * 
 * @author ogre
 * 
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
abstract public class RestAsyncTask<Params, Progress, Result> extends
        KhandroidAsyncTask<Params, Progress, RestResult<Result>> {
    
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
    protected RestResult<Result> doInBackground(Params... params) {
        RestResult<Result> ret;

        RestExchange<Result> x = createRestExchange();
        try {
            mRestFunc.execute(x);
            if (x.isOk()) {
                ret = new RestResult<Result>(RestResult.STATUS_OK, x.getResult());
            } else {
                ret = new RestResult<Result>(RestResult.STATUS_SOFT_ERROR, null);
            }
        } catch (RestExchangeFailedException e) {
            ret = new RestResult<Result>(RestResult.STATUS_HARD_ERROR, null);
        }

        return ret;
    }
}
