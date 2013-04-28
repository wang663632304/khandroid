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
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.github.khandroid.http.ParcelableCookie;

import khandroid.ext.apache.http.client.CookieStore;
import khandroid.ext.apache.http.cookie.Cookie;
import khandroid.ext.apache.http.impl.client.AbstractHttpClient;
import khandroid.ext.apache.http.impl.client.BasicCookieStore;
import khandroid.ext.apache.http.impl.cookie.BasicClientCookie;


public class HttpFunctionalityWCookiesImpl extends HttpFunctionalityImpl implements
        HttpFunctionalityWCookies, HttpFunctionalityWCookiesHelper {

    private BasicCookieStore mCookies;


    public HttpFunctionalityWCookiesImpl(AbstractHttpClient httpClient) {
        super(httpClient);
        mCookies = new BasicCookieStore();
        httpClient.setCookieStore(mCookies);
    }


    public HttpFunctionalityWCookiesImpl(AbstractHttpClient httpClient, BasicCookieStore cookies) {
        super(httpClient);

        if (cookies != null) {
            mCookies = cookies;
            httpClient.setCookieStore(mCookies);
        } else {
            throw new IllegalArgumentException("Parameter cookies is null");
        }
    }


    @Override
    public ArrayList<ParcelableCookie> prepareParcelableCookies(AbstractHttpClient httpClient) {
        ArrayList<ParcelableCookie> ret = new ArrayList<ParcelableCookie>();

        CookieStore cs = httpClient.getCookieStore();
        List<Cookie> l = cs.getCookies();
        for (Cookie c : l) {
            ParcelableCookie tmpCookie = new ParcelableCookie();
            tmpCookie.setName(c.getName());
            tmpCookie.setValue(c.getValue());
            tmpCookie.setDomain(c.getDomain());
            tmpCookie.setPath(c.getPath());
            tmpCookie.setExpiryDate(c.getExpiryDate());
            tmpCookie.setVersion(c.getVersion());
            ret.add(tmpCookie);
        }

        return ret;
    }


    @Override
    public boolean cookieExists(Cookie cookie) {
        boolean ret = false;

        List<Cookie> l = mCookies.getCookies();
        for (Cookie c : l) {
            if (c.equals(cookie)) {
                ret = true;
                break;
            }
        }

        return ret;
    }


    /**
     * Adds new cookie or replaces existing
     * 
     * @param cookie
     */
    @Override
    public void setCookie(Cookie cookie) {
        mCookies.addCookie(cookie);
    }


    @Override
    public boolean cookieExists(String name, String domain, String path) {
        boolean ret = false;

        List<Cookie> l = mCookies.getCookies();
        for (Cookie c : l) {
            if (c.getName().equals(name) && c.getDomain().equals(domain)
                    && c.getPath().equals(path)) {
                ret = true;
                break;
            }
        }

        return ret;
    }


    public Cookie getCookie(String name, String domain, String path) {
        Cookie ret = null;

        List<Cookie> l = getHttpClient().getCookieStore().getCookies();
        for (Cookie c : l) {
            if (c.getName().equals(name) && c.getDomain().equals(domain)
                    && c.getPath().equals(path)) {
                ret = c;
                break;
            }
        }

        return ret;
    }


    /**
     * Returns first cookie with the given name
     * 
     * Usually you work with one and the same site so all of the cookies have same domain and path.
     * In such case it is save to retrieve cookie just by name.
     * However, if you work with several sites/paths this method is not safe because it will return the first cookie
     * with the given name which may not be what you want.
     * 
     * @param name
     * @return Value of the cookie or <code>null</code> if not found
     */
    public Cookie getCookie(String name) {
        Cookie ret = null;

        List<Cookie> l = getHttpClient().getCookieStore().getCookies();
        for (Cookie c : l) {
            if (c.getName().equals(name)) {
                ret = c;
                break;
            }
        }

        return ret;
    }


    public String getCookieValue(String name, String domain, String path) {
        String ret = null;

        Cookie c = getCookie(name, domain, path);
        if (c != null) {
            ret = c.getValue();
        }

        return ret;
    }


    /**
     * Not safe. @see #getCookie(String)
     */
    public String getCookieValue(String name) {
        String ret = null;

        Cookie c = getCookie(name);
        if (c != null) {
            ret = c.getValue();
        }

        return ret;
    }


    @Override
    public BasicCookieStore loadIncommingCookies(String paramKey,
                                                 Bundle savedInstanceState,
                                                 Intent i) {
        BasicCookieStore cookies = new BasicCookieStore();

        Bundle extras;
        if (savedInstanceState == null) {
            extras = i.getExtras();
        } else {
            extras = savedInstanceState;
        }

        if (extras != null && extras.containsKey(paramKey)) {
            ArrayList<Parcelable> tmp = extras.getParcelableArrayList(paramKey);
            for (Parcelable p : tmp) {
                ParcelableCookie c = (ParcelableCookie) p;
                BasicClientCookie c2 = new BasicClientCookie(c.getName(), c.getValue());
                c2.setDomain(c.getDomain());
                c2.setPath(c.getPath());
                c2.setVersion(c.getVersion());
                c2.setExpiryDate(c.getExpiryDate());

                cookies.addCookie(c2);
            }
        }

        return cookies;
    }


    @Override
    protected AbstractHttpClient getHttpClient() {
        return ((AbstractHttpClient) super.getHttpClient());
    }
}
