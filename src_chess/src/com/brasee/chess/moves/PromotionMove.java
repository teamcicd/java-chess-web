package com.brasee.chess.moves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Bishop;
import com.brasee.chess.pieces.Knight;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Queen;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;
import com.brasee.chess.pieces.Piece.PieceType;

public class PromotionMove extends AbstractMove {

	private static List<PieceType> promotionPieceTypes = 
		Arrays.asList(new PieceType[] {PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT});
	
	private Piece promotionPiece;
	private Piece opposingPiece;
	
	public PromotionMove(Piece piece, Square startSquare,
			Square promotionSquare, Piece promotionPiece, Piece opposingPiece) {
		this.piece = piece;
		this.startSquare = startSquare;
		this.endSquare = promotionSquare;
		this.promotionPiece = promotionPiece;
		this.opposingPiece = opposingPiece;
	}

	@Override
	public MoveType moveType() {
		return MoveType.PROMOTION;
	}

	@Override
	public void undo(Board board) {
		board.removePiece(endSquare);
		if (opposingPiece != null) {
			board.placePiece(endSquare, opposingPiece);
		}
		board.placePiece(startSquare, piece);
	}

	public static boolean canBeExecuted(Board board, Square startSquare, Square endSquare, PieceType pieceType) {
		boolean canBeExecuted = false;
		
		if (board != null) {
			Piece piece = board.pieceOn(startSquare);
			if (piece != null && PieceType.PAWN.equals(piece.pieceType()) 
				&& promotionPieceTypes.contains(pieceType) && endSquare.inLastRowForColor(piece.color())) {
				if (piece.canMove(board, startSquare, endSquare) ||
					piece.canAttack(board, startSquare, endSquare)) {
					canBeExecuted = true;
				}
			}
		}

		return canBeExecuted;
	}

	public static Move execute(Board board, Square startSquare, Square endSquare, PieceType pieceType) {
		Piece piece = board.pieceOn(startSquare);
		Piece promotionPiece = createPromotionPiece(pieceType, piece.color());
		Piece opposingPiece = null;
		
		if (board.hasPieceOn(endSquare)) {
			opposingPiece = board.pieceOn(endSquare);
			board.removePiece(endSquare);
		}
		board.removePiece(startSquare);
		board.placePiece(endSquare, promotionPiece);
		
		return new PromotionMove(piece, startSquare, endSquare, promotionPiece, opposingPiece);
	}
	
	public Piece promotionPiece() {
		return promotionPiece;
	}
	
	public Piece opposingPiece() {
		return opposingPiece;
	}

	private static Piece createPromotionPiece(PieceType pieceType, Color color) {
		Piece promotionPiece = null;
		
		if (PieceType.QUEEN.equals(pieceType)) {
			promotionPiece = new Queen(color);
		}
		else if (PieceType.ROOK.equals(pieceType)) {
			promotionPiece = new Rook(color);
		}
		else if (PieceType.BISHOP.equals(pieceType)) {
			promotionPiece = new Bishop(color);
		}
		else if (PieceType.KNIGHT.equals(pieceType)) {
			promotionPiece = new Knight(color);
		}
		
		return promotionPiece;
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
		updatedSquares.put(endSquare, promotionPiece);
		return updatedSquares;
	}

	@Override
	public Piece capturedPiece() {
		return opposingPiece;
	}

}
