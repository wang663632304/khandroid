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

import java.io.IOException;

import com.github.khandroid.http.KhandroidBasicResponseHandler;
import com.github.khandroid.misc.KhandroidLog;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;


abstract public class RestExchange {
	private RestRequest mRequest;
	private boolean mExecuted = false;
	private RestResponse mResp;
	private Object mResult;
	private boolean mIsOk;

	
	abstract protected RestRequest createRequest();
	abstract protected Object createResult(String source) throws MalformedResponseException;

	
	public RestRequest getRequest() {
		if (mRequest == null) {
			mRequest = createRequest();
		}
		
		return mRequest;
	}


	public void perform(HttpClient httpClient) throws ClientProtocolException, IOException, MalformedResponseException {
		ResponseHandler<String> responseHandler = new KhandroidBasicResponseHandler();
		HttpUriRequest req = getRequest().createHttpRequest();
		KhandroidLog.d("Executing: "+ req.getURI().toString());
		String rawResponse = httpClient.execute(req, responseHandler);
		
		RestResponseParser rp = new RestResponseParser();
		mResp = rp.parse(rawResponse);
		if (mResp.getStatus() == RestResponse.RESPONSE_STATUS_OK) {
			KhandroidLog.d("Response is OK");
			mResult = createResult(mResp.getPayload());
			mIsOk = true;
		} else {
			KhandroidLog.d("Response is ERROR: " + mResp.getStatus());
			mIsOk = false;
		}
			
		mExecuted = true;
	}
	
	
	public boolean isOk() {
		if (mExecuted) {
//			if (result != null) {
//				return true;
//			} else {
//				return false;
//			}
		    return mIsOk;
		} else {
			throw new IllegalStateException("Exchange was not performed.");
		}
	}
	
	
	public int getErrorCode() {
		if (mExecuted) {
			return mResp.getResponseCode();
		} else {
			throw new IllegalStateException("Exchange was not performed.");
		}
	}
	
	
	public String getErrorMessage() {
		if (mExecuted) {
			return mResp.getPayload();
		} else {
			throw new IllegalStateException("Exchange was not performed.");
		}
	}
	
	
	public Object getResult() {
		if (isOk()) {
			return mResult;
		} else {
			throw new IllegalStateException("Trying to get result from exchange that ended in error.");			
		}
	}
}
