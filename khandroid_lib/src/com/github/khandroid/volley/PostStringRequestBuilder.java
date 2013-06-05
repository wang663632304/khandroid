package com.github.khandroid.volley;

import java.util.Map;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;


public class PostStringRequestBuilder extends RequestBuilder<String> implements PostRequestBuilder {
    private PostRequestBuilderImpl postHelper = new PostRequestBuilderImpl();
    
	public PostStringRequestBuilder(String url) {
		super(url);
	}

	
	public StringRequest build() {
	    StringRequest ret = null;
		
		ret = new StringRequest(Request.Method.POST, buildUriString(), getSuccessListener(), getErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
		        return PostStringRequestBuilder.this.getPostParameters();
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
