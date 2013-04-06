package com.github.khandroid.http;

import java.io.IOException;

import khandroid.ext.apache.http.client.HttpResponseException;
import khandroid.ext.apache.http.impl.client.BasicResponseHandler;
import khandroid.ext.apache.http.util.EntityUtils;

public class KhandroidBasicResponseHandler extends BasicResponseHandler {
	public String handleResponse(final khandroid.ext.apache.http.HttpResponse response)
									throws HttpResponseException,
									IOException {
		khandroid.ext.apache.http.StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			khandroid.ext.apache.http.HttpEntity entity = response.getEntity();
			if (entity != null) {
				EntityUtils.consume(entity);
			}
			
			throw new HttpResponseException(statusLine.getStatusCode(),
											statusLine.getReasonPhrase());
		}

		khandroid.ext.apache.http.HttpEntity entity = response.getEntity();
		return entity == null ? null : EntityUtils.toString(entity);
	}
}