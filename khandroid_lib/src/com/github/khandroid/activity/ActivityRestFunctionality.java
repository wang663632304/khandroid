package com.github.khandroid.activity;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.CookieStore;
import khandroid.ext.apache.http.cookie.Cookie;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import khandroid.ext.apache.http.impl.cookie.BasicClientCookie;

import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchangeFailedException;


abstract public class ActivityRestFunctionality extends ActivityHttpFunctionality implements RestFunctionality {
	public ActivityRestFunctionality(HostingAble activity) {
		super((HostActivity) activity);
	}

	
	public void executeExchange(RestExchange x) throws RestExchangeFailedException {
		try {
			DefaultHttpClient httpClient = getHttpClient();
			injectSessionCookie(httpClient, ((HostingAble) getActivity()).retrieveSessionCookie());
			
//			String rawResponse = httpClient.execute(x.getRequest().createHttpRequest(), responseHandler);

			try {
				x.perform(httpClient);
				((HostingAble) getActivity()).saveSessionCoookie(extractSessionCookie(httpClient.getCookieStore()));
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
				// This happens when app is cold started and there are still no http request executed agains the server 
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
			Cookie saveCookie = ((HostingAble) getActivity()).retrieveSessionCookie(); 
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
	
	
	public interface HostingAble extends ActivityAttachable.HostingAble {
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
