package com.falatrabalho.FalaTrabalho.textimprovement.infrastructure;

public class LlamaCppException extends RuntimeException {

	public LlamaCppException(String message) {
		super(message);
	}

	public LlamaCppException(String message, Throwable cause) {
		super(message, cause);
	}
}
