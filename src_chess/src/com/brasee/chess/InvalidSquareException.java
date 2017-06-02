package com.brasee.chess;

public class InvalidSquareException extends RuntimeException {

	private static final long serialVersionUID = -7638303455648116089L;
	
	public InvalidSquareException(String message) {
		super(message);
	}

}
