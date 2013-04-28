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

import java.util.ArrayList;

import khandroid.ext.apache.http.impl.client.AbstractHttpClient;
import khandroid.ext.apache.http.impl.client.BasicCookieStore;

import android.content.Intent;
import android.os.Bundle;

import com.github.khandroid.http.ParcelableCookie;

public interface HttpFunctionalityWCookiesHelper {
    public ArrayList<ParcelableCookie> prepareParcelableCookies(AbstractHttpClient httpClient);
    BasicCookieStore loadIncommingCookies(String paramKey, Bundle savedInstanceState, Intent i);
}
