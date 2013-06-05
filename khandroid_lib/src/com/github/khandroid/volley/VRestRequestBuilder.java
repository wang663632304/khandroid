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

package com.github.khandroid.volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.github.khandroid.misc.KhandroidLog;


abstract public class VRestRequestBuilder extends RequestBuilder<JSONObject> {
    private VRestRequest.Listener mListener;
    
    
    public VRestRequestBuilder(String url) {
        super(url);
    }
    
    
    public void setRestListener(VRestRequest.Listener listener) {
        mListener = listener;
    }
    
    
    protected VRestRequest.Listener getRestListener() {
        return mListener;
    }
    
    
    public class SuccessListener implements com.android.volley.Response.Listener<JSONObject> {
        private VRestRequest.Listener mListener;
        
        public SuccessListener(VRestRequest.Listener listener) {
            super();
            mListener = listener;
        }
        

        @Override
        public void onResponse(JSONObject response) {
            try {
                int status = response.getInt(VRestRequest.RESPONSE_KEY_STATUS);
                int responseCode = response.getInt(VRestRequest.RESPONSE_KEY_RESPONSE_CODE);
                JSONObject payload = response.getJSONObject(VRestRequest.RESPONSE_KEY_PAYLOAD);
                
                if (status == VRestRequest.RESPONSE_STATUS_OK) {
                    mListener.onSuccess(payload, responseCode);
                } else if (status == VRestRequest.RESPONSE_STATUS_ERR) {
                    mListener.onSoftError(responseCode);
                } else {
                    KhandroidLog.e("Invalid Json structure for the response.");
                    mListener.onHardError();
                }
            } catch (JSONException e) {
                if (mListener != null) {
                    KhandroidLog.e("Invalid Json structure for the response.");
                    mListener.onHardError();
                }
            }
        }
    }
    
    
    public class ErrorListener implements com.android.volley.Response.ErrorListener {
        private VRestRequest.Listener mListener;
        
        public ErrorListener(VRestRequest.Listener listener) {
            super();
            mListener = listener;
        }
                
        
        @Override
        public void onErrorResponse(VolleyError error) {
            if (mListener != null) {
                mListener.onHardError();
            }
        }
    }
}
