package com.brasee.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece.Color;

public class BishopTest {

	private Piece bishop;
	private Board board;
	
	@Before
	public void setUp() {
		bishop = new Bishop(Color.WHITE);
		board = new Board();
	}
	
	@Test
	public void testCanMoveDiagonallyUpAndLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c6");
		board.placePiece(currentSquare, bishop);
		assertTrue(bishop.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyUpAndRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e6");
		board.placePiece(currentSquare, bishop);
		assertTrue(bishop.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyDownAndLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c4");
		board.placePiece(currentSquare, bishop);
		assertTrue(bishop.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDiagonallyDownAndRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e4");
		board.placePiece(currentSquare, bishop);
		assertTrue(bishop.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanAttackDiagonallyUpAndLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c6");
		board.placePiece(currentSquare, bishop);
		board.placePiece(occupiedSquare, new Bishop(Color.BLACK));
		assertTrue(bishop.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyUpAndRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e6");
		board.placePiece(currentSquare, bishop);
		board.placePiece(occupiedSquare, new Bishop(Color.BLACK));
		assertTrue(bishop.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyDownAndLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c4");
		board.placePiece(currentSquare, bishop);
		board.placePiece(occupiedSquare, new Bishop(Color.BLACK));
		assertTrue(bishop.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDiagonallyDownAndRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e4");
		board.placePiece(currentSquare, bishop);
		board.placePiece(occupiedSquare, new Bishop(Color.BLACK));
		assertTrue(bishop.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanMoveDiagonallyMultipleSquares() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("h8");
		board.placePiece(currentSquare, bishop);
		assertTrue(bishop.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testCannotMoveHorizontally() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("b1");
		board.placePiece(currentSquare, bishop);
		assertFalse(bishop.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testCannotMoveVertically() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a2");
		board.placePiece(currentSquare, bishop);
		assertFalse(bishop.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testCannotMoveToSameSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a1");
		board.placePiece(currentSquare, bishop);
		assertFalse(bishop.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackSameSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a1");
		board.placePiece(currentSquare, bishop);
		assertFalse(bishop.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveToOccupiedSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("b2");
		board.placePiece(currentSquare, bishop);
		board.placePiece(occupiedSquare, new Bishop(Color.WHITE));
		assertFalse(bishop.canMove(board, currentSquare, occupiedSquare));		
	}
	
	@Test
	public void testCannotMoveWhenBlocked() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("h8");
		board.placePiece(currentSquare, bishop);
		board.placePiece(new Square("b2"), new Bishop(Color.WHITE));
		assertFalse(bishop.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testCannotAttackWhenBlocked() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("h8");
		board.placePiece(currentSquare, bishop);
		board.placePiece(new Square("b2"), new Bishop(Color.WHITE));
		board.placePiece(new Square("h8"), new Bishop(Color.BLACK));
		assertFalse(bishop.canAttack(board, currentSquare, occupiedSquare));	
	}
	
	@Test
	public void testCannotAttackEmptySquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("h8");
		board.placePiece(currentSquare, bishop);
		assertFalse(bishop.canAttack(board, currentSquare, emptySquare));		
	}
}
