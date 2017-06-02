package com.brasee.chess.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public class NormalMove extends AbstractMove {

	private NormalMove(Piece piece, Square startSquare, Square endSquare) {
		this.piece = piece;
		this.startSquare = startSquare;
		this.endSquare = endSquare;
	}

	public static boolean canBeExecuted(Board board, Square startSquare, Square endSquare) {
		boolean canBeExecuted = false;
		
		if (board != null) {
			Piece piece = board.pieceOn(startSquare);
			if (piece != null && piece.canMove(board, startSquare, endSquare)) {
				canBeExecuted = true;
			}
		}
		
		return canBeExecuted;
	}

	public static NormalMove execute(Board board, Square startSquare, Square endSquare) {
		Piece piece = board.pieceOn(startSquare);
		board.movePiece(piece, startSquare, endSquare);
		piece.incrementTimesMoved();
		return new NormalMove(piece, startSquare, endSquare);
	}
	
	@Override
	public void undo(Board board) {
		board.movePiece(piece, endSquare, startSquare);
		piece.decrementTimesMove();
	}

	@Override
	public MoveType moveType() {
		return MoveType.NORMAL;
	}

	@Override
	public List<Square> clearedSquares() {
		List<Square> clearedSquares = new ArrayList<Square>();
		clearedSquares.add(startSquare);
		return clearedSquares;
	}

	@Override
	public Map<Square, Piece> updatedSquares() {
		Map<Square, Piece> updatedSquares = new HashMap<Square, Piece>();
		updatedSquares.put(endSquare, piece);
		return updatedSquares;
	}

	@Override
	public Piece capturedPiece() {
		return null;
	}
}
