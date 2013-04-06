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
