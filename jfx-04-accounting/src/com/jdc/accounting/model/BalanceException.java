package com.jdc.accounting.model;

public class BalanceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BalanceException(String message) {
		super(message);
	}

	public BalanceException(Throwable cause) {
		super(cause);
	}

}
