package com.brasee.chess.pieces;

import com.brasee.chess.Board;
import com.brasee.chess.Square;

public interface Piece {

	public enum Color {
		WHITE, BLACK
	}
	
	public enum PieceType {
		PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
	}
	
	public boolean canMove(Board board, Square currentSquare, Square emptySquare);
	public boolean canAttack(Board board, Square currentSquare, Square occupiedSquare);
	public boolean isFirstMove();
	public void incrementTimesMoved();
	public void decrementTimesMove();
	public Color color();
	public PieceType pieceType();
	
}
