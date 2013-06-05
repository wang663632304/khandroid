package com.github.khandroid.volley;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;


public class PostJsonObjectRequestBuilder extends RequestBuilder<JSONObject> implements PostRequestBuilder {
    private PostRequestBuilderImpl postHelper = new PostRequestBuilderImpl();
    
    public PostJsonObjectRequestBuilder(String url) {
        super(url);
    }

    
    public JsonObjectRequest build() {
        JsonObjectRequest ret = null;
        
        ret = new JsonObjectRequest(Request.Method.POST, buildUriString(), null, getSuccessListener(), getErrorListener()) {
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
