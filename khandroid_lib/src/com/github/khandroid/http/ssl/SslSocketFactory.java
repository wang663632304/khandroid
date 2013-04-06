package com.github.khandroid.http.ssl;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import khandroid.ext.apache.http.conn.ssl.SSLSocketFactory;


class SslSocketFactory extends SSLSocketFactory {
	public SslSocketFactory(InputStream keyStore, String keyStorePassword) throws GeneralSecurityException {
		super(createSSLContext(keyStore, keyStorePassword), STRICT_HOSTNAME_VERIFIER);
	}


	private static SSLContext createSSLContext(InputStream keyStore, String keyStorePassword) throws GeneralSecurityException {
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new com.github.khandroid.http.ssl.KhandroidX509TrustManager(keyStore, keyStorePassword) }, null);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Failure initializing default SSL context", e);
		} catch (KeyManagementException e) {
			throw new IllegalStateException("Failure initializing default SSL context", e);
		}

		return sslcontext;		
	}
}    


