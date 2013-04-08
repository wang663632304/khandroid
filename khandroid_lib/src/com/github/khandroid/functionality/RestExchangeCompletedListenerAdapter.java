package com.github.khandroid.functionality;

import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestResponse;


/**
 * Adapter for {@link RestExchange.CompletedListener} 
 * Use it if you are interested only in
 * RestExchange.CompletedListener#exchangeOk event and don't want
 * to implement RestExchange.CompletedListener#exchangeCompleted, RestExchange.CompletedListener#exchangeCompletedFail
 * and RestExchange.CompletedListener#exchangeCompletedEpicFail
 * 
 * @param <T>
 */
public abstract class RestExchangeCompletedListenerAdapter<T> implements RestExchange.CompletedListener<T> {
    public void exchangeCompleted(RestExchange<T> x) {
    }


    public void exchangeCompletedFail(RestResponse response) {
    }


    public void exchangeCompletedEpicFail() {
    }
}