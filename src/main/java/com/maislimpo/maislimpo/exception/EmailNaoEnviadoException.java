package com.maislimpo.maislimpo.exception;

public class EmailNaoEnviadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailNaoEnviadoException(String message) {
        super(message);
    }
}
