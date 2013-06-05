package com.github.khandroid.volley;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import khandroid.ext.apache.http.NameValuePair;
import khandroid.ext.apache.http.client.utils.URIBuilder;
import khandroid.ext.apache.http.client.utils.URLEncodedUtils;
import khandroid.ext.apache.http.message.BasicNameValuePair;


abstract public class RequestBuilder<T> {
    private String mProtocol;
    private String mDomain;
    private String mPath;
    private int mPort;

    private Listener<T> mSuccessListener;
    private ErrorListener mErrorListener;

    private Map<String, String> mGetParams = new HashMap<String, String>();

    private static final String CHARSET = "UTF-8";


    public RequestBuilder(String url) {
        super();

        if (url != null) {
            URI tmpUri;
            try {
                tmpUri = new URI(url);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid URL: " + url, e);
            }

            setProtocol(tmpUri.getScheme());
            setDomain(tmpUri.getHost());
            setPath(tmpUri.getPath());
            setPort(tmpUri.getPort());

            for (NameValuePair pair : URLEncodedUtils.parse(tmpUri, CHARSET)) {
                mGetParams.put(pair.getName(), pair.getValue());
            }
        } else {
            throw new IllegalArgumentException("URL is null.");
        }
    }

    
    protected String buildUriString() {
        String ret;
        
        String protocol = getProtocol();
        if (protocol != null && !protocol.equals("") && getDomain() != null && !getDomain().equals("") && getPath() != null && !getPath().equals("")) {
            URI uri;
            try {
                URIBuilder ub = new URIBuilder();
                ub.setScheme(protocol).setHost(getDomain()).setPort(getPort()).setPath(getPath());
                ub.setQuery(URLEncodedUtils.format(prepareGetParameters(), "UTF-8"));
                uri = ub.build();
            } catch (URISyntaxException e) {
                throw new IllegalStateException("Error creating URI.", e);
            }
            
            ret = uri.toString();
        } else {
            throw new IllegalStateException("Some of required fields (protocol, domain, path) are empty.");
        }
        
        return ret;
    }
    
    
    public ArrayList<NameValuePair> prepareGetParameters() {
        ArrayList<NameValuePair> ret = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : mGetParams.entrySet()) {
            ret.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        
        return ret;
    }
    

    abstract public Request<?> build();


    public void setParameter(String key, String value) {
        mGetParams.put(key, value);
    }
    
    
    public Map<String, String> getGetParams() {
        return mGetParams;
    }


    public String getProtocol() {
        return mProtocol;
    }


    public void setProtocol(String protocol) {
        String protoLower = protocol.toLowerCase(Locale.ENGLISH);
        if (protoLower.equals("http") || protoLower.equals("https")) {
            this.mProtocol = protocol;
        } else {
            throw new IllegalArgumentException("Protocol must be http ot https");
        }
    }


    public String getDomain() {
        return mDomain;
    }


    public void setDomain(String domain) {
        this.mDomain = domain;
    }


    public String getPath() {
        return mPath;
    }


    public void setPath(String path) {
        this.mPath = path;
    }


    public int getPort() {
        return mPort;
    }


    public void setPort(int port) {
        this.mPort = port;
    }


    public void setSuccessListener(Listener<T> successListener) {
        mSuccessListener = successListener;
    }


    public void setErrorListener(ErrorListener errorListener) {
        mErrorListener = errorListener;
    }


    protected Listener<T> getSuccessListener() {
        return mSuccessListener;
    }


    protected ErrorListener getErrorListener() {
        return mErrorListener;
    }

}
