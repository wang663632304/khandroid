package com.github.khandroid.functionality;

import java.io.IOException;
import java.net.UnknownHostException;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;

import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.fragment.functionalities.FragmentHttpFunctionality;
import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchangeFailedException;


abstract public class RestFunctionalityImplementation extends FragmentHttpFunctionality implements RestFunctionality {
    public RestFunctionalityImplementation(HostingAble fragment) {
        super((HostFragment) fragment);
    }

    
    public <T> void executeExchange(RestExchange<T> x, RestExchange.CompletedListener<T> listener) {
        try {
            executeExchange(x);
            if (listener != null) {
                listener.exchangeCompleted(x);
            }
            if (x.isOk()) {
                T result = x.getResult();
                if (listener != null) {
                    listener.exchangeCompletedOk(result);
                }
            } else {
                if (listener != null) {
                    listener.exchangeCompletedFail(x.getResponse());
                }
            }
        } catch (RestExchangeFailedException e) {
            if (listener != null) {
                listener.exchangeCompletedEpicFail();
            }
        }
    }
    
    
    public <T> void executeExchange(RestExchange<T> x) throws RestExchangeFailedException {
        try {
            DefaultHttpClient httpClient = getHttpClient();
//          String rawResponse = httpClient.execute(x.getRequest().createHttpRequest(), responseHandler);
            try {
                x.perform(httpClient);
            } catch (MalformedResponseException e) {
                throw new RestExchangeFailedException("Parsing response failed because of malformed response from server.", e);
            }
        } catch (ClientProtocolException e) {
            throw new RestExchangeFailedException("Executing request failed because of protocol violation.", e);
        } catch (UnknownHostException e) {
            throw new RestExchangeFailedException("Executing request failed. Unknown host. Is there internet connection?", e);
        } catch (IOException e) {
            throw new RestExchangeFailedException("Executing request failed.", e);
        }
    }
}
