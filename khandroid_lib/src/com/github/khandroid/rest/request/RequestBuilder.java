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


package com.github.khandroid.rest.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import khandroid.ext.apache.http.NameValuePair;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;
import khandroid.ext.apache.http.client.utils.URLEncodedUtils;
import khandroid.ext.apache.http.message.BasicNameValuePair;


abstract public class RequestBuilder {
	private String protocol;
	private String domain;
	private String path;
	private int port;
	
	ArrayList<NameValuePair> getParams = new ArrayList<NameValuePair>();
	
	private static final String CHARSET = "UTF-8";
	
	
	public RequestBuilder(String url) {
		super();

		if (url != null) {
			URI tmpUri;
			try {
				tmpUri = new URI(url);
			} catch (URISyntaxException e) {
				throw new IllegalArgumentException("Invalid URL: " + url, e);
			}
			
			setProtocol(tmpUri.getScheme());
			setDomain(tmpUri.getHost());
			setPath(tmpUri.getPath());
			setPort(tmpUri.getPort());

			
			for(NameValuePair pair : URLEncodedUtils.parse(tmpUri, CHARSET)) {
				getParams.add(pair);	
			}
		} else {
			throw new IllegalArgumentException("URL is null.");
		}
		
	}
	
	
	abstract public HttpUriRequest build();
	
	
	
	public void addParameter(String key, String value) {
		if (!isParameterPresent(key)) {
			NameValuePair tmp = new BasicNameValuePair(key, value);
			getParams.add(tmp);
		} else {
			throw new IllegalArgumentException("There is already parameter named " + key);
		}
	}
	
	
	public boolean isParameterPresent(String key) {
		boolean ret = false;
		
		for(NameValuePair pair : getParams) {
			if (pair.getName().equals(key)) {
				ret = true;
				break;
			}
		}
		
		return ret;
	}	
	
	
	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		String protoLower = protocol.toLowerCase();
		if (protoLower.equals("http") || protoLower.equals("https")) {
			this.protocol = protocol;
		} else {
			throw new IllegalArgumentException("Protocol must be http ot https");
		}
	}
	

	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	
	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public ArrayList<NameValuePair> getGetParams() {
		return getParams;
	}	
}
