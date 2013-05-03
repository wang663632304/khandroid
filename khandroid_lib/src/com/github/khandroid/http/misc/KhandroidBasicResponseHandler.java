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


package com.github.khandroid.http.misc;

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