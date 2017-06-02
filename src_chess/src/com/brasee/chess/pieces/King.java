package com.brasee.chess.pieces;

import com.brasee.chess.Board;
import com.brasee.chess.Square;

public class King extends AbstractPiece {

	public King(Color color) {
		super(color);
	}
	
	@Override
	public boolean canAttack(Board board, Square currentSquare, Square occupiedSquare) {
		return (validSetupForAttack(board, currentSquare, occupiedSquare) &&
				areAdjacentSquares(currentSquare, occupiedSquare));
	}

	@Override
	public boolean canMove(Board board, Square currentSquare, Square emptySquare) {
		return (validSetupForMove(board, currentSquare, emptySquare) &&
				areAdjacentSquares(currentSquare, emptySquare));
	}
	
	private boolean areAdjacentSquares(Square square1, Square square2) {
		boolean adjacentHorizontally = Math.abs(square1.distanceBetweenFile(square2)) == 1 &&
			square1.distanceBetweenRank(square2) == 0;
		boolean adjacentVertically = Math.abs(square1.distanceBetweenRank(square2)) == 1 &&
			square1.distanceBetweenFile(square2) == 0;
		boolean adjacentDiagonally = Math.abs(square1.distanceBetweenFile(square2)) == 1 &&
			Math.abs(square1.distanceBetweenRank(square2)) == 1;
		return adjacentHorizontally || adjacentVertically || adjacentDiagonally;
	}

	@Override
	public PieceType pieceType() {
		return PieceType.KING;
	}

}
