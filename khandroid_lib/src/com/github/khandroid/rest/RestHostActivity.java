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

import android.os.Bundle;

import com.github.khandroid.core.HostActivity;
import com.github.khandroid.functionality.HttpFunctionality;


abstract public class RestHostActivity extends HostActivity {
    private ActivityRestFunctionality mRestFunc;
    abstract protected HttpFunctionality createHttpFunctionality();
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mRestFunc = new ActivityRestFunctionality(this, createHttpFunctionality());
        mRestFunc.onCreate(savedInstanceState);
    }

    
    protected <T> void executeRestExchange(RestExchange<T> x, RestExchange.CompletedListener<T> listener) {
        
    }
}
