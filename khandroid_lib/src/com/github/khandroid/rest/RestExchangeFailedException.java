package com.github.khandroid.rest;

@SuppressWarnings("serial")
public class RestExchangeFailedException extends Exception {

	public RestExchangeFailedException() {
		super();
	}

	public RestExchangeFailedException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public RestExchangeFailedException(String detailMessage) {
		super(detailMessage);
	}

	public RestExchangeFailedException(Throwable throwable) {
		super(throwable);
	}

}
