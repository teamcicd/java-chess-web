package com.brasee.chess.pieces;

import com.brasee.chess.Board;
import com.brasee.chess.Square;

public class Queen extends AbstractPiece {

	public Queen(Color color) {
		super(color);
	}
	
	@Override
	public boolean canAttack(Board board, Square currentSquare, Square occupiedSquare) {
		return (validSetupForAttack(board, currentSquare, occupiedSquare) &&
				board.clearPathBetween(currentSquare, occupiedSquare));
	}

	@Override
	public boolean canMove(Board board, Square currentSquare, Square emptySquare) {
		return (validSetupForMove(board, currentSquare, emptySquare) &&
				board.clearPathBetween(currentSquare, emptySquare));
	}
	
	@Override
	public PieceType pieceType() {
		return PieceType.QUEEN;
	}

}
