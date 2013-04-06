package com.github.khandroid.activity;

import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchangeFailedException;


public interface RestFunctionality {
    public void executeExchange(RestExchange x) throws RestExchangeFailedException;
}
