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


package com.github.khandroid.rest;

import org.json.JSONException;
import org.json.JSONObject;




public class RestResponseParser {
	public RestResponse parse(String response) throws MalformedResponseException {
		try {
			JSONObject respJson = new JSONObject(response);
			int status = respJson.getInt("status");
			if (status == RestResponse.RESPONSE_STATUS_OK || status == RestResponse.RESPONSE_STATUS_ERR) {
				int tmpResponseCode = respJson.getInt("response_code");
				int responseCode = tmpResponseCode;
				String payload = respJson.getString("payload");
				
				return new RestResponse(status, responseCode, payload);
			} else {
				throw new MalformedResponseException("Invalid status returned from server ;" + status);
			}
		} catch (JSONException e) {
			throw new MalformedResponseException("Unable to interpret the string as JSON data strcuture.", e);  
		}
	}
}
