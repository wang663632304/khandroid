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

import java.util.Map;

import com.android.volley.Request;


public class PostVRestRequestBuilder extends VRestRequestBuilder implements PostRequestBuilder {
    private PostRequestBuilderImpl postHelper = new PostRequestBuilderImpl();
    
    public PostVRestRequestBuilder(String url) {
        super(url);
    }

    
    public VRestRequest build() {
        VRestRequest ret = null;
        
        VRestRequest.Listener restListener = getRestListener();
        
        ret = new VRestRequest(Request.Method.POST, 
                               buildUriString(), 
                               null, 
                               new SuccessListener(restListener), 
                               new ErrorListener(restListener)) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                return getPostParameters();
            }
        };
        
        return ret;
    }


    @Override
    public void setPostParameter(String key, String value) {
        postHelper.setPostParameter(key, value);
    }


    @Override
    public Map<String, String> getPostParameters() {
        return postHelper.getPostParameters();
    }
}
