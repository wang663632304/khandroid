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


package com.github.khandroid.fragment.functionalities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.rest.ParcelableCookie;

import khandroid.ext.apache.http.client.CookieStore;
import khandroid.ext.apache.http.cookie.Cookie;
import khandroid.ext.apache.http.impl.client.BasicCookieStore;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import khandroid.ext.apache.http.impl.cookie.BasicClientCookie;


abstract public class FragmentHttpWCookiesFunctionality extends FragmentHttpFunctionality {
    public static final String COOKIES_PARAM_NAME = "_cookies";
    private BasicCookieStore mCookies;


    public FragmentHttpWCookiesFunctionality(HostFragment fragment) {
        super(fragment);
        getFragment().setRetainInstance(true);
    }
    
    
    public FragmentHttpWCookiesFunctionality(HostFragment fragment, BasicCookieStore cookies) {
        this(fragment);
        this.mCookies = cookies;
    }   

    
    public void startActivityForResultWithCookies(Intent intent, int requestCode) {
        intent.putParcelableArrayListExtra(COOKIES_PARAM_NAME, prepareParcelableCookies());
        getFragment().getActivity().startActivityForResult(intent, requestCode);
    }


    public void startActivityWithCookies(Intent intent) {
        intent.putParcelableArrayListExtra(COOKIES_PARAM_NAME, prepareParcelableCookies());
        getFragment().getActivity().startActivity(intent);
    }


    protected void addCookie(Cookie cookie) {
        mCookies.addCookie(cookie);
    }


    protected boolean cookieExists(Cookie cookie) {
        boolean ret = false;
        
        List<Cookie> l = mCookies.getCookies();
        for(Cookie c : l) {
            if (c.equals(cookie)) {
                ret = true;
                break;
            }
        }
        
        return ret;
    }
    
    
    protected boolean cookieExists(String name, String domain, String path) {
        boolean ret = false;
        
        List<Cookie> l = mCookies.getCookies();
        for(Cookie c : l) {
            if (c.getName().equals(name) && c.getDomain().equals(domain) && c.getPath().equals(path)) {
                ret = true;
                break;
            }
        }
        
        return ret;
    }
    
    
    protected String getCookieValue(String name, String domain, String path) {
        String ret = null;
        
        List<Cookie> l = mCookies.getCookies();
        for(Cookie c : l) {
            if (c.getName().equals(name) && c.getDomain().equals(domain) && c.getPath().equals(path)) {
                ret = c.getValue();
                break;
            }
        }
        
        return ret;
    }
    
    
    public DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = (DefaultHttpClient) super.getHttpClient();
        httpClient.setCookieStore(mCookies);

        return httpClient;
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadIncommingCookies(savedInstanceState, getFragment().getActivity().getIntent());
    }   


    private void loadIncommingCookies(Bundle savedInstanceState, Intent i) {
        if (mCookies == null) {
            mCookies = new BasicCookieStore();
        }
        
        Bundle extras;
        if (savedInstanceState == null) {
            extras = i.getExtras();
        } else {
            extras = savedInstanceState;
        }

        if (extras != null && extras.containsKey(COOKIES_PARAM_NAME)) {
            ArrayList<Parcelable> tmp = extras.getParcelableArrayList(COOKIES_PARAM_NAME);
            for (Parcelable p : tmp) {
                ParcelableCookie c = (ParcelableCookie) p;
                BasicClientCookie c2 = new BasicClientCookie(c.getName(), c.getValue());
                c2.setDomain(c.getDomain());
                c2.setPath(c.getPath());
                c2.setVersion(c.getVersion());
                c2.setExpiryDate(c.getExpiryDate());
                
                mCookies.addCookie(c2);
            }
        }
    }


    private ArrayList<ParcelableCookie> prepareParcelableCookies() {
        ArrayList<ParcelableCookie> ret = new ArrayList<ParcelableCookie>();

        if (isHttpClientInitialized()) {
            DefaultHttpClient httpClient = getHttpClient();
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
        } else {
            KhandroidLog.tw("You are using startActivityForResultWithCookies() or startActivityWithCookies() before calling getHttpClient(), i.e. there are no cookies created yet.");
        }

        return ret;
    }
}