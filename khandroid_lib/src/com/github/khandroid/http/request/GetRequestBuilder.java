package com.github.khandroid.http.request;

import java.net.URI;
import java.net.URISyntaxException;


import khandroid.ext.apache.http.client.methods.HttpGet;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;
import khandroid.ext.apache.http.client.utils.URIBuilder;
import khandroid.ext.apache.http.client.utils.URLEncodedUtils;

public class GetRequestBuilder extends RequestBuilder {
	public GetRequestBuilder(String url) {
		super(url);
	}

	
	@Override
	public HttpUriRequest build() {
		HttpGet ret = null;
		
		String protocol = getProtocol();
		if (protocol != null && !protocol.equals("") && getDomain() != null && !getDomain().equals("") && getPath() != null && !getPath().equals("")) {
			URI uri;
			try {
				URIBuilder ub = new URIBuilder();
				ub.setScheme(protocol).setHost(getDomain()).setPort(getPort()).setPath(getPath());
				ub.setQuery(URLEncodedUtils.format(getGetParams(), "UTF-8"));
				uri = ub.build();
			} catch (URISyntaxException e) {
				throw new IllegalStateException("Error creating URI.", e);
			}
			
			ret = new HttpGet(uri);
			ret.addHeader("Accept-Charset", "UTF-8,*");
		} else {
			throw new IllegalStateException("Some of required fields (protocol, domain, path) are empty.");
		}

		return ret;
	}

}
