package com.maislimpo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailNaoConfirmadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailNaoConfirmadoException(String message) {
        super(message);
    }
}
