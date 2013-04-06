package com.github.khandroid.http.ssl;

import java.io.InputStream;
import java.security.GeneralSecurityException;

import com.github.khandroid.misc.KhandroidLog;

import khandroid.ext.apache.http.client.params.ClientPNames;
import khandroid.ext.apache.http.conn.ClientConnectionManager;
import khandroid.ext.apache.http.conn.scheme.PlainSocketFactory;
import khandroid.ext.apache.http.conn.scheme.Scheme;
import khandroid.ext.apache.http.conn.scheme.SchemeRegistry;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import khandroid.ext.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import khandroid.ext.apache.http.params.HttpParams;


public class SslHttpClient extends DefaultHttpClient {
	private InputStream keyStore;
	private String keyStorePassword;
	
	@SuppressWarnings("unused")
	private SslHttpClient() {
		// no parameterless constructor
	}

	
	public SslHttpClient(InputStream keyStore, String keyStorePassword) {
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
	}	
	

	public SslHttpClient(ClientConnectionManager conman, InputStream keyStore, String keyStorePassword) {
		super(conman);
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
	}


	public SslHttpClient(final ClientConnectionManager conman, 
	                    		final HttpParams params,
	                    		InputStream keyStore, 
	                    		String keyStorePassword
	                    		) {
		super(conman, checkForInvalidParams(params));
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
	}
	
	
    public SslHttpClient(final HttpParams params, InputStream keyStore, String keyStorePassword) {
        super(null, checkForInvalidParams(params));
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
    }	
    

    private static HttpParams checkForInvalidParams(HttpParams params) {
	    String className = (String) params.getParameter(ClientPNames.CONNECTION_MANAGER_FACTORY_CLASS_NAME);
        if (className != null) {
        	throw new IllegalArgumentException("Don't try to pass ClientPNames.CONNECTION_MANAGER_FACTORY_CLASS_NAME parameter. We use our own connection manager factory anyway...");
        }
        
        return params;
    }
 
    
    @Override
    protected ClientConnectionManager createClientConnectionManager() {
		SchemeRegistry registry = new SchemeRegistry();
		
		PlainSocketFactory pfs = PlainSocketFactory.getSocketFactory();
		Scheme s = new Scheme("http", 80, pfs);
		registry.register(s);
		
		ThreadSafeClientConnManager ret; 
		try {
			registry.register(new Scheme("https", 443, new SslSocketFactory(keyStore, keyStorePassword)));
			ret = new ThreadSafeClientConnManager(registry); 
		} catch (GeneralSecurityException e) {
			KhandroidLog.d("Cannot create socket factor.", e);
			ret = null;
		}
    	
		
		return ret;
    }
}


