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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


import khandroid.ext.apache.http.NameValuePair;
import khandroid.ext.apache.http.client.entity.UrlEncodedFormEntity;
import khandroid.ext.apache.http.client.methods.HttpPost;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;
import khandroid.ext.apache.http.client.utils.URIUtils;
import khandroid.ext.apache.http.client.utils.URLEncodedUtils;
import khandroid.ext.apache.http.message.BasicNameValuePair;


public class PostRequestBuilder extends GetRequestBuilder {
	ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
	
	public PostRequestBuilder(String url) {
		super(url);
	}

	
	public HttpUriRequest build() {
		HttpPost ret = null;
		
		String protocol = getProtocol();
		if (protocol != null && !protocol.equals("") && getDomain() != null && !getDomain().equals("") && getPath() != null && !getPath().equals("")) {
			URI uri;
			try {
				uri = URIUtils.createURI(protocol, getDomain(), getPort(), getPath(),
				             			URLEncodedUtils.format(getGetParams(), "UTF-8"), null);
			} catch (URISyntaxException e) {
				throw new IllegalStateException("Error creating URI.", e);
			}
			
			ret = new HttpPost(uri);
			ret.addHeader("Accept-Charset", "UTF-8,*");
			
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, "UTF-8");
				ret.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}		
			
		} else {
			throw new IllegalStateException("Some of required fields (protocol, domain, path) are empty.");
		}		
		
		return ret;
	}
	
	
	public void addPostParameter(String key, String value) {
		if (!isPostParameterPresent(key)) {
			NameValuePair tmp = new BasicNameValuePair(key, value);
			postParams.add(tmp);
		} else {
			throw new IllegalArgumentException("There is already parameter named " + key);
		}
	}
	
	
	public boolean isPostParameterPresent(String key) {
		boolean ret = false;
		
		for(NameValuePair pair : postParams) {
			if (pair.getName().equals(key)) {
				ret = true;
				break;
			}
		}
		
		return ret;
	}		
}
