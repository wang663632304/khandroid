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

import com.github.khandroid.core.ActivityAttachedFunctionality;
import com.github.khandroid.core.HostActivity;
import com.github.khandroid.functionality.HttpFunctionality;
import com.github.khandroid.functionality.RestFunctionality;
import com.github.khandroid.functionality.RestFunctionalityImpl;
import com.github.khandroid.rest.RestExchange.CompletedListener;


public class ActivityRestFunctionality extends ActivityAttachedFunctionality implements
        RestFunctionality {
    private final RestFunctionalityImpl mRestFunc;
    private boolean mAutoShutdown = true;


    public ActivityRestFunctionality(HostActivity activity, HttpFunctionality mHttpFunc) {
        super(activity);
        mRestFunc = new RestFunctionalityImpl(mHttpFunc);
    }


    @Override
    public <T> String execute(RestExchange<T> x) throws RestExchangeFailedException {
        return mRestFunc.execute(x);
    }


    @Override
    public <T> String execute(RestExchange<T> x, CompletedListener<T> listener) {
        return mRestFunc.execute(x, listener);
    }
    
    
    public boolean setAutoShutdown(boolean value) {
        boolean ret = mAutoShutdown;
        mAutoShutdown = value;
        return ret;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAutoShutdown) {
            shutDown();
        }
    }


    @Override
    public void shutDown() {
        mRestFunc.shutDown();
    }
}
