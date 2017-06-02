package com.brasee.chess;

public class SquareAlreadyOccupiedException extends RuntimeException {

	private static final long serialVersionUID = 8383546673380371549L;
	
	public SquareAlreadyOccupiedException(Square square) {
		super(square.toString() + " is already occupied");
	}

}
