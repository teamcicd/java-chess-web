package com.brasee.chess.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Piece.PieceType;

public class EnPassantMove extends AbstractMove {

	private Piece opposingPiece;
	private Square opposingPieceSquare;
	
	private EnPassantMove(Piece piece, Square startSquare, Square endSquare, Piece opposingPawn,
			Square opposingPawnSquare) {
		this.piece = piece;
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		this.opposingPiece = opposingPawn;
		this.opposingPieceSquare = opposingPawnSquare;
	}

	public static boolean canBeExecuted(Board board, Square startSquare, Square endSquare, Move lastMove) {
		boolean canBeExecuted = false;
		
		Piece piece = board.pieceOn(startSquare);
		if (piece != null && PieceType.PAWN.equals(piece.pieceType())) {
			Pawn pawn = (Pawn) piece;
			if (pawn.canMoveEnPassant(board, startSquare, endSquare)) {
				Piece opposingPawn = board.pieceOn(new Square(endSquare.file(), startSquare.rank()));
				if (opposingPawn != null && PieceType.PAWN.equals(opposingPawn.pieceType()) &&
					lastMove != null && lastMove.piece() == opposingPawn && 
					Math.abs(lastMove.startSquare().distanceBetweenRank(lastMove.endSquare())) == 2) {
					canBeExecuted = true;
				}
			}
		}
		
		return canBeExecuted;
	}

	public static EnPassantMove execute(Board board, Square startSquare, Square endSquare) {
		Piece pawn = board.pieceOn(startSquare);
		Square opposingPawnSquare = new Square(endSquare.file(), startSquare.rank());
		Piece opposingPawn = board.pieceOn(opposingPawnSquare);
		board.movePiece(pawn, startSquare, endSquare);
		board.removePiece(opposingPawnSquare);
		pawn.incrementTimesMoved();
		return new EnPassantMove(pawn, startSquare, endSquare, opposingPawn, opposingPawnSquare);
	}

	@Override
	public MoveType moveType() {
		return MoveType.EN_PASSANT;
	}

	@Override
	public void undo(Board board) {
		piece.decrementTimesMove();
		board.movePiece(piece, endSquare, startSquare);
		board.placePiece(opposingPieceSquare, opposingPiece);
	}
	
	public Piece opposingPiece() {
		return opposingPiece;
	}
	
	public Square opposingPieceSquare() {
		return opposingPieceSquare;
	}

	@Override
	public List<Square> clearedSquares() {
		List<Square> clearedSquares = new ArrayList<Square>();
		clearedSquares.add(startSquare);
		clearedSquares.add(opposingPieceSquare);
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
