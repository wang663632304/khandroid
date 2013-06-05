package com.github.khandroid.volley;

import java.util.HashMap;
import java.util.Map;

public class PostRequestBuilderImpl implements PostRequestBuilder {
    private Map<String, String> mPostParams = new HashMap<String, String>();
    
    public void setPostParameter(String key, String value) {
        mPostParams.put(key, value);
    }

    
    public Map<String, String> getPostParameters() {
        return mPostParams;
    }
}
