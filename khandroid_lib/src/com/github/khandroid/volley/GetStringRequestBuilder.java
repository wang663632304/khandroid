package com.github.khandroid.volley;

import com.android.volley.toolbox.StringRequest;

public class GetStringRequestBuilder extends RequestBuilder<String> {

    public GetStringRequestBuilder(String url) {
    	super(url);
    }
    
    
    @Override
    public StringRequest build() {
        StringRequest ret = null;
    	
		ret = new StringRequest(buildUriString(), getSuccessListener(), getErrorListener());
    
    	return ret;
    }
}
