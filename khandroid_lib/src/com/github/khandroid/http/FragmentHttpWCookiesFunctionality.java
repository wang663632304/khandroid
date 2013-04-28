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

package com.github.khandroid.http;

import khandroid.ext.apache.http.cookie.Cookie;
import khandroid.ext.apache.http.impl.client.AbstractHttpClient;
import khandroid.ext.apache.http.impl.client.BasicCookieStore;

import android.content.Intent;
import android.os.Bundle;

import com.github.khandroid.core.HostFragment;
import com.github.khandroid.functionality.HttpFunctionalityWCookies;
import com.github.khandroid.functionality.HttpFunctionalityWCookiesImpl;


public class FragmentHttpWCookiesFunctionality extends FragmentHttpFunctionality implements HttpFunctionalityWCookies {
    public static final String COOKIES_PARAM_NAME = "_cookies";
    private final AbstractHttpClient mHttpClient;
    private final HttpFunctionalityWCookiesImpl mHttpFunc;

    
    public FragmentHttpWCookiesFunctionality(HostFragment fragment, AbstractHttpClient httpClient) {
        super(fragment, httpClient);
        if (httpClient != null) {
            mHttpFunc = new HttpFunctionalityWCookiesImpl(httpClient, new BasicCookieStore());
            mHttpClient = httpClient;
        } else {
            throw new IllegalArgumentException("Parameter httpClient is null");
        }
    }


    public FragmentHttpWCookiesFunctionality(HostFragment fragment,
            AbstractHttpClient httpClient,
            BasicCookieStore cookies) {
        super(fragment, httpClient);
        if (cookies != null) {
            if (httpClient != null) {
                mHttpFunc = new HttpFunctionalityWCookiesImpl(httpClient, cookies);
                mHttpClient = httpClient;
            } else {
                throw new IllegalArgumentException("Parameter httpClient is null");
            }
        } else {
            throw new IllegalArgumentException("Parameter cookies is null");
        }
    }


    public void startActivityForResultWithCookies(Intent intent, int requestCode) {
        intent.putParcelableArrayListExtra(COOKIES_PARAM_NAME, mHttpFunc.prepareParcelableCookies(mHttpClient));
        getActivity().startActivityForResult(intent, requestCode);
    }


    public void startActivityWithCookies(Intent intent) {
        intent.putParcelableArrayListExtra(COOKIES_PARAM_NAME, mHttpFunc.prepareParcelableCookies(mHttpClient));
        getActivity().startActivity(intent);
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BasicCookieStore cookies = mHttpFunc.loadIncommingCookies(COOKIES_PARAM_NAME,
                                                  savedInstanceState,
                                                  getActivity().getIntent());
        mHttpClient.setCookieStore(cookies);
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(COOKIES_PARAM_NAME, mHttpFunc.prepareParcelableCookies(mHttpClient));
    }


    @Override
    public boolean cookieExists(Cookie cookie) {
        return mHttpFunc.cookieExists(cookie);
    }


    @Override
    public boolean cookieExists(String name, String domain, String path) {
        return mHttpFunc.cookieExists(name, domain, path);
    }


    @Override
    public void setCookie(Cookie cookie) {
        mHttpFunc.setCookie(cookie);
    }


    @Override
    public String getCookieValue(String name, String domain, String path) {
        return mHttpFunc.getCookieValue(name, domain, path);
    }


    @Override
    public String getCookieValue(String name) {
        return mHttpFunc.getCookieValue(name);
    }


    @Override
    public Cookie getCookie(String name, String domain, String path) {
        return mHttpFunc.getCookie(name, domain, path);
    }


    @Override
    public Cookie getCookie(String name) {
        return mHttpFunc.getCookie(name);
    }
}
