package com.brasee.chess.pieces;

import com.brasee.chess.Board;
import com.brasee.chess.Square;

public class Bishop extends AbstractPiece {

	public Bishop(Color color) {
		super(color);
	}
	
	@Override
	public boolean canAttack(Board board, Square currentSquare, Square occupiedSquare) {
		return (validSetupForAttack(board, currentSquare, occupiedSquare) &&
				board.clearPathBetween(currentSquare, occupiedSquare) &&
				currentSquare.inDiagonalPathWith(occupiedSquare));
	}

	@Override
	public boolean canMove(Board board, Square currentSquare, Square emptySquare) {
		return (validSetupForMove(board, currentSquare, emptySquare) && 
				board.clearPathBetween(currentSquare, emptySquare) &&
				currentSquare.inDiagonalPathWith(emptySquare));
	}

	@Override
	public PieceType pieceType() {
		return PieceType.BISHOP;
	}

}
