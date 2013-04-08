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


package com.github.khandroid.activity.functionalities.defaults;

import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;

import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.activity.functionalities.ActivityHttpFunctionality;

/**
 * Serves as a default implementation of {@link ActivityHttpFunctionality}, i.e. 
 * creating <code>DefaultHttpClient</code>.
 * 
 * @see ActivityHttpFunctionality
 * @author ogre
 *
 */
public class DefaultActivityHttpFunctionality extends ActivityHttpFunctionality {
    public DefaultActivityHttpFunctionality(HostActivity activity) {
        super(activity);
    }

    @Override
    protected HttpClient createHttpClient() {
        return new DefaultHttpClient();
    }
}
