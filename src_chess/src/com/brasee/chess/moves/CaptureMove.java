package com.brasee.chess.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public class CaptureMove extends AbstractMove {

	private Piece opposingPiece;
	
	private CaptureMove(Piece piece, Square startSquare, Square endSquare, Piece opposingPiece) {
		this.piece = piece;
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		this.opposingPiece = opposingPiece;
	}

	public static boolean canBeExecuted(Board board, Square startSquare, Square endSquare) {
		boolean canBeExecuted = false;
		
		if (board != null) {
			Piece piece = board.pieceOn(startSquare);
			if (piece != null && piece.canAttack(board, startSquare, endSquare)) {
				canBeExecuted = true;
			}
		}
		
		return canBeExecuted;
	}

	public static CaptureMove execute(Board board, Square startSquare, Square endSquare) {
		Piece piece = board.pieceOn(startSquare);
		Piece opposingPiece = board.pieceOn(endSquare);
		board.removePiece(endSquare);
		board.movePiece(piece, startSquare, endSquare);		
		piece.incrementTimesMoved();
		return new CaptureMove(piece, startSquare, endSquare, opposingPiece);
	}
	
	@Override
	public void undo(Board board) {
		piece.decrementTimesMove();
		board.movePiece(piece, endSquare, startSquare);
		board.placePiece(endSquare, opposingPiece);
	}

	@Override
	public MoveType moveType() {
		return MoveType.CAPTURE;
	}

	public Piece opposingPiece() {
		return opposingPiece;
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
		return opposingPiece;
	}

}
