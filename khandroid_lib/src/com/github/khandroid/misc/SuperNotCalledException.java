package com.github.khandroid.misc;

@SuppressWarnings("serial")
public class SuperNotCalledException extends IllegalStateException {
	public SuperNotCalledException() {
		super();
	}

	public SuperNotCalledException(String message, Throwable cause) {
		super(message, cause);
	}

	public SuperNotCalledException(String detailMessage) {
		super(detailMessage);
	}

	public SuperNotCalledException(Throwable cause) {
		super(cause);
	}
}
