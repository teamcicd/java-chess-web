package com.brasee.chess.pieces;

import com.brasee.chess.Board;
import com.brasee.chess.Square;

public class Pawn extends AbstractPiece {

	public Pawn(Color color) {
		super(color);
	}
	
	@Override
	public boolean canAttack(Board board, Square currentSquare, Square occupiedSquare) {
		if (validSetupForAttack(board, currentSquare, occupiedSquare) &&
			occupiedSquare.distanceBetweenRank(currentSquare) == forwardDistanceForColor(1) && 
				Math.abs(occupiedSquare.distanceBetweenFile(currentSquare)) == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean canMove(Board board, Square currentSquare, Square emptySquare) {
		if (validSetupForMove(board, currentSquare, emptySquare) &&
			emptySquare.inSameFileAs(currentSquare) &&
			emptySquare.distanceBetweenRank(currentSquare) == forwardDistanceForColor(1)) {
			return true;
		}
		else if (isFirstMove() && 
				validSetupForMove(board, currentSquare, emptySquare) &&
				board.clearPathBetween(currentSquare, emptySquare) &&
				emptySquare.distanceBetweenRank(currentSquare) == forwardDistanceForColor(2) &&
				emptySquare.inSameFileAs(currentSquare)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean canMoveEnPassant(Board board, Square currentSquare, Square emptySquare) {
		if (validSetupForMove(board, currentSquare, emptySquare) && 
				Math.abs(emptySquare.distanceBetweenFile(currentSquare)) == 1 &&
				emptySquare.distanceBetweenRank(currentSquare) == forwardDistanceForColor(1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private int forwardDistanceForColor(int forwardDistance) {
		if (color() == Color.WHITE) {
			return forwardDistance;
		}
		else {
			return forwardDistance * -1;
		}
	}

	@Override
	public PieceType pieceType() {
		return PieceType.PAWN;
	}
	
}
