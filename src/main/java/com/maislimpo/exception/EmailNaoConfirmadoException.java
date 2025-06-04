package com.maislimpo.exception;

public class EmailNaoConfirmadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailNaoConfirmadoException(String message) {
        super(message);
    }
}
