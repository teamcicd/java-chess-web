package com.brasee.chess.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.King;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;

public class CastlingMove extends AbstractMove {

	private Piece rook;
	private Square rookStartSquare;
	private Square rookEndSquare;
	
	private CastlingMove(Piece piece, Square startSquare, Square endSquare, Piece rook,
			Square rookStartSquare, Square rookEndSquare) {
		this.piece = piece;
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		this.rook = rook;
		this.rookStartSquare = rookStartSquare;
		this.rookEndSquare = rookEndSquare;
	}

	public static boolean canBeExecuted(Board board, Square startSquare, Square endSquare) {
		boolean canBeExecuted = false;
				
		Piece king = board.pieceOn(startSquare);
		if (king != null && king instanceof King && king.isFirstMove()) {
			int horizontalSquaresMove = endSquare.distanceBetweenFile(startSquare);
			if (endSquare.inSameRankAs(startSquare) && Math.abs(horizontalSquaresMove) == 2) {
				char rookFile = (horizontalSquaresMove == 2 ? 'h' : 'a');
				Square rookSquare = new Square(rookFile, startSquare.rank());
				Piece rook = board.pieceOn(rookSquare);
				if (rook != null && rook instanceof Rook && rook.isFirstMove() &&
					board.clearPathBetween(startSquare, rookSquare) &&
					!castlingSquaresInCheck(board, king.color(), startSquare, endSquare)) {
					canBeExecuted = true;
				}
			}
		}
		
		return canBeExecuted;
	}

	private static boolean castlingSquaresInCheck(Board board, Color color, Square startSquare, Square endSquare) {
		boolean castlingSquaresInCheck = false;
		
		// check the square the king is on now
		if (board.inCheck(color)) {
			castlingSquaresInCheck = true;
		}
		else {
			// check the pass-through square and the end square
			char fileBetweenStartAndEnd = (endSquare.file() > startSquare.file() ? 'f' : 'd');
			Square squareBetweenStartAndEnd = new Square(fileBetweenStartAndEnd, startSquare.rank());
			for (Square square : new Square[] {squareBetweenStartAndEnd, endSquare}) {
				if (!castlingSquaresInCheck && castlingSquareInCheck(board, square, color)) {
					castlingSquaresInCheck = true;
				}
			}
		}
		
		return castlingSquaresInCheck;
	}

	private static boolean castlingSquareInCheck(Board board, Square square, Color color) {
		boolean castlingSquareInCheck = false;
		
		if (!board.hasPieceOn(square)) {
			// place a temp piece on square so that canAttack evaluates correctly
			Piece tempPiece = new King(color);
			board.placePiece(square, tempPiece);
			if (board.squareInCheck(square, color)) {
				castlingSquareInCheck = true;
			}
		
			// remove the temp piece
			board.removePiece(square);
		}
		
		return castlingSquareInCheck;
	}

	public static CastlingMove execute(Board board, Square kingStartSquare, Square kingEndSquare) {
		Square rookStartSquare = null, rookEndSquare = null;
		if (kingEndSquare.distanceBetweenFile(kingStartSquare) == 2) {
			rookStartSquare = new Square('h', kingStartSquare.rank());
			rookEndSquare = new Square('f', kingStartSquare.rank());
		}
		else {
			rookStartSquare = new Square('a', kingStartSquare.rank());
			rookEndSquare = new Square('d', kingStartSquare.rank());
		}
		Piece king = board.pieceOn(kingStartSquare);
		Piece rook = board.pieceOn(rookStartSquare);
		board.movePiece(king, kingStartSquare, kingEndSquare);
		board.movePiece(rook, rookStartSquare, rookEndSquare);
		king.incrementTimesMoved();
		rook.incrementTimesMoved();
		return new CastlingMove(king, kingStartSquare, kingEndSquare, rook, rookStartSquare, rookEndSquare);
	}
	
	@Override
	public void undo(Board board) {
		piece.decrementTimesMove();
		rook.decrementTimesMove();
		board.movePiece(piece, endSquare, startSquare);
		board.movePiece(rook, rookEndSquare, rookStartSquare);
	}

	@Override
	public MoveType moveType() {
		return MoveType.CASTLING;
	}

	public Piece rook() {
		return rook;
	}
	
	public Square rookStartSquare() {
		return rookStartSquare;
	}
	
	public Square rookEndSquare() {
		return rookEndSquare;
	}

	@Override
	public List<Square> clearedSquares() {
		List<Square> clearedSquares = new ArrayList<Square>();
		clearedSquares.add(startSquare);
		clearedSquares.add(rookStartSquare);
		return clearedSquares;
	}

	@Override
	public Map<Square, Piece> updatedSquares() {
		Map<Square, Piece> updatedSquares = new HashMap<Square, Piece>();
		updatedSquares.put(endSquare, piece);
		updatedSquares.put(rookEndSquare, rook);
		return updatedSquares;
	}

	@Override
	public Piece capturedPiece() {
		return null;
	}

}
