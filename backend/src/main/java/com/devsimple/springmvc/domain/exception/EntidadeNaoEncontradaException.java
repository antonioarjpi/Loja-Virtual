package com.devsimple.springmvc.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}

	public EntidadeNaoEncontradaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
