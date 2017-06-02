package com.brasee.games.chess.web;

import com.brasee.chess.Square;

public class JsonSquare {

	private String square;
	
	public JsonSquare(Square square) {
		this.setSquare(square.toString());
	}

	public void setSquare(String square) {
		this.square = square;
	}

	public String getSquare() {
		return square;
	}
}
