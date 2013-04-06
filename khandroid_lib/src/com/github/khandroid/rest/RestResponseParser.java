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
