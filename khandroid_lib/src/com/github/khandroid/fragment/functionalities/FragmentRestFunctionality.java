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

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.CookieStore;
import khandroid.ext.apache.http.cookie.Cookie;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import khandroid.ext.apache.http.impl.cookie.BasicClientCookie;

import com.github.khandroid.fragment.FragmentAttachable;
import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.fragment.FragmentAttachable.HostingAble;
import com.github.khandroid.functionality.RestFunctionality;
import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchangeFailedException;


abstract public class FragmentRestFunctionality extends FragmentHttpFunctionality implements RestFunctionality {
    public FragmentRestFunctionality(HostingAble fragment) {
        super((HostFragment) fragment);
    }

    
    public void executeExchange(RestExchange x) throws RestExchangeFailedException {
        try {
            DefaultHttpClient httpClient = getHttpClient();
            injectSessionCookie(httpClient, ((HostingAble) getFragment()).retrieveSessionCookie());
            
//          String rawResponse = httpClient.execute(x.getRequest().createHttpRequest(), responseHandler);

            try {
                x.perform(httpClient);
                ((HostingAble) getFragment()).saveSessionCoookie(extractSessionCookie(httpClient.getCookieStore()));
            } catch (MalformedResponseException e) {
                throw new RestExchangeFailedException("Parsing response failed because of malformed response from server.", e);
            }
        } catch (ClientProtocolException e) {
            throw new RestExchangeFailedException("Executing request failed because of protocol violation.", e);
        } catch (UnknownHostException e) {
            throw new RestExchangeFailedException("Executing request failed. Unknown host. Is there internet connection?", e);
        } catch (IOException e) {
            throw new RestExchangeFailedException("Executing request failed.", e);
        }
    }
    
    
    /**
     * Injects the session cookie if not already present
     * 
     * @param httpClient
     * @param sessionCookie SessionCookie
     */
    private void injectSessionCookie(DefaultHttpClient httpClient, Cookie sessionCookie) {
        CookieStore cs = httpClient.getCookieStore();
        cs.clearExpired(new Date());
        if (!isSessionCookiePresent(cs, sessionCookie)) {
            if (sessionCookie != null) {
                // sessionCookie may come "empty", i.e. getValue() returns null or "". 
                // This happens when app is cold started and there are still no http request executed against the server 
                if (sessionCookie.getValue() != null && !sessionCookie.getValue().equals("")) {
                    cs. addCookie(sessionCookie);
                }
            }
        }
    }
    
    
    private BasicClientCookie extractSessionCookie(CookieStore cs) {
        BasicClientCookie ret = null;
        
        List<Cookie> l = cs.getCookies();
        for (Cookie c : l) {
            Cookie saveCookie = ((HostingAble) getFragment()).retrieveSessionCookie(); 
            if (c.getName().equals(saveCookie.getName()) && 
                        c.getDomain().equals(saveCookie.getDomain()) && 
                        c.getPath().equals(saveCookie.getPath())) {
                ret = (BasicClientCookie) c;
                break;
            }
        }   
        
        return ret;
    }   
    
    
    protected boolean isSessionCookiePresent(CookieStore cs, Cookie srcCookie) {
        boolean ret = false;
        
        List<Cookie> l = cs.getCookies();
        for (Cookie c : l) {
            if (c.getName().equals(srcCookie.getName()) && 
                    c.getDomain().equals(srcCookie.getDomain()) && 
                    c.getPath().equals(srcCookie.getPath())) {
                ret = true;
                break;
            }
        }
    
        return ret;
    }       
    
    
    public interface HostingAble extends FragmentAttachable.HostingAble {
        /**
         * Must save the cookie somewhere (in the Application object for example)
         */
        void saveSessionCoookie(BasicClientCookie c);
        
        /**
         * Must retrieve the cookie saved by saveSessionCoookie() or return "empty" cookie with same name, 
         * host and path (e.g. as would be set as server) otherwise session cookie preserving mechanism will not work 
         */
        BasicClientCookie retrieveSessionCookie();
    }   

}
