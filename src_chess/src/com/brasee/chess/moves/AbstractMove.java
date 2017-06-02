package com.brasee.chess.moves;

import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public abstract class AbstractMove implements Move {

	protected Piece piece;
	protected Square startSquare;
	protected Square endSquare;
	
	@Override
	public Square endSquare() {
		return endSquare;
	}

	@Override
	public Piece piece() {
		return piece;
	}

	@Override
	public Square startSquare() {
		return startSquare;
	}
	
	@Override
	public String notation() {
		if (startSquare != null && endSquare != null) {
			return startSquare.toString() + "." + endSquare.toString();
		}
		else {
			return null;
		}
	}
	
	@Override
	public abstract MoveType moveType();
	
	@Override
	public abstract void undo(Board board);
	
	@Override
	public abstract Map<Square, Piece> updatedSquares();
	
	@Override
	public abstract List<Square> clearedSquares();

	@Override
	public abstract Piece capturedPiece();
	
}
