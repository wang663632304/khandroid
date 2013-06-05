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

import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.JsonObjectRequest;


public class VRestRequest extends JsonObjectRequest {
    public static String RESPONSE_KEY_STATUS = "status";
    public static String RESPONSE_KEY_PAYLOAD = "payload";
    public static String RESPONSE_KEY_RESPONSE_CODE = "response_code";
    
    
    public static int RESPONSE_STATUS_OK = 1;
    public static int RESPONSE_STATUS_ERR = 0;

    
    VRestRequest(String url,
            JSONObject jsonRequest,
            com.android.volley.Response.Listener<JSONObject> listener,
            ErrorListener errorListener) {
        
        super(url, jsonRequest, listener, errorListener);
    }
    
    VRestRequest(int method, 
                 String url,
                 JSONObject jsonRequest,
                 com.android.volley.Response.Listener<JSONObject> listener,
                 ErrorListener errorListener) {
             
             super(method, url, jsonRequest, listener, errorListener);
         }


    public interface Listener {
        void onSuccess(JSONObject json, int responseCode);
        void onSoftError(int responseCode);
        void onHardError();
    }
}

