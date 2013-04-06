package com.github.khandroid.rest;

@SuppressWarnings("serial")
public class MalformedResponseException extends Exception {
	public MalformedResponseException() {
		super();
	}

	public MalformedResponseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public MalformedResponseException(String detailMessage) {
		super(detailMessage);
	}

	public MalformedResponseException(Throwable throwable) {
		super(throwable);
	}
}
