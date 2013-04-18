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

public class RestResponse {
	public static int RESPONSE_STATUS_OK = 1;
	public static int RESPONSE_STATUS_ERR = 0;
	
	
	private int status;
	private int responseCode;
	private String payload;
	
	
	public RestResponse(int status, int responseCode, String payload) {
		super();
		this.status = status;
		this.responseCode = responseCode;
		this.payload = payload;
	}
	
	
	public RestResponse(RestResponse resp) {
		super();
		this.status = resp.getStatus();
		this.responseCode = resp.getResponseCode();
		this.payload = resp.getPayload();
	}


	public int getStatus() {
		if (status == -1) {
			throw new IllegalStateException("Status is = -1, i.e. undetermined.");
		}
		
		return status;
	}

	
	public int getResponseCode() {
		return responseCode;
	}	
	

	public String getPayload() {
		return payload;
	}
	
	
	public boolean isOk() {
		return getStatus() == RESPONSE_STATUS_OK;
	}
	
	
	public boolean isError() {
		return getStatus() == RESPONSE_STATUS_ERR;
	}
}
