package com.maislimpo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) 
public class EmailNaoEnviadoException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public EmailNaoEnviadoException(String message) {
        super(message);
    }
}
