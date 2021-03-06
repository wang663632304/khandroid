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

package com.github.khandroid.http.ssl;

import java.io.InputStream;
import java.security.GeneralSecurityException;

import com.github.khandroid.misc.KhandroidLog;

import khandroid.ext.apache.http.conn.ClientConnectionManager;
import khandroid.ext.apache.http.conn.scheme.PlainSocketFactory;
import khandroid.ext.apache.http.conn.scheme.Scheme;
import khandroid.ext.apache.http.conn.scheme.SchemeRegistry;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import khandroid.ext.apache.http.impl.conn.PoolingClientConnectionManager;
import khandroid.ext.apache.http.params.HttpParams;


public class DefaultSslHttpClient extends DefaultHttpClient {
	private InputStream keyStore;
	private String keyStorePassword;
	
	@SuppressWarnings("unused")
	private DefaultSslHttpClient() {
		// no parameterless constructor
	}

	
	public DefaultSslHttpClient(InputStream keyStore, String keyStorePassword) {
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
	}	
	

	public DefaultSslHttpClient(ClientConnectionManager conman, InputStream keyStore, String keyStorePassword) {
		super(conman);
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
	}


	public DefaultSslHttpClient(final ClientConnectionManager conman, 
	                    		final HttpParams params,
	                    		InputStream keyStore, 
	                    		String keyStorePassword
	                    		) {
		super(conman, params);
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
	}
	
	
    public DefaultSslHttpClient(final HttpParams params, InputStream keyStore, String keyStorePassword) {
        super(null, params);
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
    }	
 
    
    @Override
    protected ClientConnectionManager createClientConnectionManager() {
		SchemeRegistry registry = new SchemeRegistry();
		
		PlainSocketFactory pfs = PlainSocketFactory.getSocketFactory();
		Scheme s = new Scheme("http", 80, pfs);
		registry.register(s);
		
		PoolingClientConnectionManager ret; 
		try {
			registry.register(new Scheme("https", 443, new SslSocketFactory(keyStore, keyStorePassword)));
			ret = new PoolingClientConnectionManager(registry); 
		} catch (GeneralSecurityException e) {
			KhandroidLog.d("Cannot create socket factor.", e);
			ret = null;
		}
    	
		
		return ret;
    }
}


