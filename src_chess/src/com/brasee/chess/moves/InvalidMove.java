package com.brasee.chess.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public class InvalidMove extends AbstractMove {

	private InvalidMove() {}
	
	public static boolean canBeExecuted() {
		return false;
	}

	public static InvalidMove execute() {
		return new InvalidMove();
	}

	@Override
	public MoveType moveType() {
		return MoveType.INVALID;
	}

	@Override
	public void undo(Board board) {
		// nothing to undo! 		
	}

	@Override
	public List<Square> clearedSquares() {
		return new ArrayList<Square>();
	}

	@Override
	public Map<Square, Piece> updatedSquares() {
		return new HashMap<Square, Piece>();
	}

	@Override
	public Piece capturedPiece() {
		return null;
	}

}
