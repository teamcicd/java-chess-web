package com.brasee.chess.pieces;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class QueenTest {

	private Piece queen;
	private Board board;
	
	@Before
	public void setUp() {
		queen = new Queen(Color.WHITE);
		board = new Board();
	}
	
	@Test
	public void testCanMoveStraightUp() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("d8");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveStraightDown() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("d1");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveStraightLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("a5");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveStraightRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("h5");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyUpAndLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("b7");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyUpAndRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("f7");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyDownAndLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("b3");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyDownAndRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("f3");
		board.placePiece(currentSquare, queen);
		assertTrue(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanAttackStraightUp() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("d8");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackStraightDown() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("d1");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackStraightLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("a5");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackStraightRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("h5");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyUpAndLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("b7");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyUpAndRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("f7");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyDownAndLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("b3");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyDownAndRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("f3");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertTrue(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveToNonStraightOrDiagonalSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("b8");
		board.placePiece(currentSquare, queen);
		assertFalse(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackNonStraightOrDiagonalSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("b8");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertFalse(queen.canMove(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveToSameSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a1");
		board.placePiece(currentSquare, queen);
		assertFalse(queen.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackSameSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a1");
		board.placePiece(currentSquare, queen);
		assertFalse(queen.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveToOccupiedSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("b1");
		board.placePiece(currentSquare, queen);
		board.placePiece(occupiedSquare, new Queen(Color.WHITE));
		assertFalse(queen.canMove(board, currentSquare, occupiedSquare));		
	}
	
	@Test
	public void testCannotMoveWhenBlocked() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("c1");
		board.placePiece(currentSquare, queen);
		board.placePiece(new Square("b1"), new Queen(Color.WHITE));
		assertFalse(queen.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testCannotAttackWhenBlocked() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("c1");
		board.placePiece(currentSquare, queen);
		board.placePiece(new Square("b1"), new Queen(Color.WHITE));
		board.placePiece(occupiedSquare, new Queen(Color.BLACK));
		assertFalse(queen.canAttack(board, currentSquare, occupiedSquare));		
	}
	
	@Test
	public void testCannotAttackEmptySquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("c1");
		board.placePiece(currentSquare, queen);
		assertFalse(queen.canAttack(board, currentSquare, emptySquare));
	}
}
