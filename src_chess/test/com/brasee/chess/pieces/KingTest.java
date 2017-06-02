package com.brasee.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece.Color;

public class KingTest {

	private Piece king;
	private Board board;
	
	@Before
	public void setUp() {
		king = new King(Color.WHITE);
		board = new Board();
	}
	
	@Test
	public void testCanMoveOneSquareStraightUp() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("d6");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareStraightDown() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("d4");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareStraightLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c5");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareStraightRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e5");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareDiagonallyUpAndLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c6");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareDiagonallyUpAndRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e6");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareDiagonallyDownAndLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c4");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveOneSquareDiagonallyDownAndRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e4");
		board.placePiece(currentSquare, king);
		assertTrue(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanAttackOneSquareStraightUp() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("d6");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareStraightDown() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("d4");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareStraightLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c5");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareStraightRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e5");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));		
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareDiagonallyUpAndLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c6");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareDiagonallyUpAndRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e6");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));		
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareDiagonallyDownAndLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c4");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));	
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackOneSquareDiagonallyDownAndRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e4");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.BLACK));		
		assertTrue(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveMultipleSquares() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a3");
		assertFalse(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotMoveToSameSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a1");
		assertFalse(king.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackSameSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a1");
		board.placePiece(currentSquare, king);
		assertFalse(king.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotAttackEmptySquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a2");
		assertFalse(king.canAttack(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotMoveToOccupiedSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a2");
		board.placePiece(currentSquare, king);
		board.placePiece(occupiedSquare, new King(Color.WHITE));
		assertFalse(king.canMove(board, currentSquare, occupiedSquare));
	}

}
