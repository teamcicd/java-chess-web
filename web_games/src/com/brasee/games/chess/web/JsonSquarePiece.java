package com.brasee.games.chess.web;

import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public class JsonSquarePiece {

	private String square;
	private String piece;
	
	public JsonSquarePiece(Square square, Piece piece) {
		this.square = square.toString();
		this.piece = pieceToJsonPiece(piece);
	}
	
	public String getSquare() {
		return square;
	}
	
	public String getPiece() {
		return piece;
	}
	
	private String pieceToJsonPiece(Piece piece) {
		String jsonPiece = piece.pieceType().toString() + "_" + piece.color().toString();
		return jsonPiece.toLowerCase();
	}
	
}
