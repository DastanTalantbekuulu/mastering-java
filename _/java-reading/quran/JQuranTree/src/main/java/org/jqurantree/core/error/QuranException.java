package org.jqurantree.core.error;

public class QuranException extends RuntimeException {

	public QuranException(String message) {
		super(message);
	}

	public QuranException(String message, Exception exception) {
		super(message, exception);
	}
}
