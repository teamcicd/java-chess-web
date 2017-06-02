package com.brasee.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece.Color;

public class RookTest {

	private Piece rook;
	private Board board;
	
	@Before
	public void setUp() {
		rook = new Rook(Color.WHITE);
		board = new Board();
	}
	
	@Test
	public void testRookCanMoveLeft() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("a5");
		board.placePiece(currentSquare, rook);
		assertTrue(rook.canMove(board, currentSquare, emptySquare));	
	}
	
	@Test 
	public void testRookCanMoveRight() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("h5");
		board.placePiece(currentSquare, rook);
		assertTrue(rook.canMove(board, currentSquare, emptySquare));			
	}
	
	@Test
	public void testRookCanMoveUp() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("d8");
		board.placePiece(currentSquare, rook);
		assertTrue(rook.canMove(board, currentSquare, emptySquare));	
	}
	
	@Test
	public void testRookCanMoveDown() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("d1");
		board.placePiece(currentSquare, rook);
		assertTrue(rook.canMove(board, currentSquare, emptySquare));	
	}
	
	@Test
	public void testRookCannotMoveDiagonal() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e6");
		board.placePiece(currentSquare, rook);
		assertFalse(rook.canMove(board, currentSquare, emptySquare));	
	}
	
	@Test
	public void testRookCanAttackLeft() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("a5");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.BLACK));
		assertTrue(rook.canAttack(board, currentSquare, occupiedSquare));	
	}
	
	@Test 
	public void testRookCanAttackRight() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("h5");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.BLACK));
		assertTrue(rook.canAttack(board, currentSquare, occupiedSquare));			
	}
	
	@Test
	public void testRookCanAttackUp() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("d8");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.BLACK));
		assertTrue(rook.canAttack(board, currentSquare, occupiedSquare));	
	}
	
	@Test
	public void testRookCanAttackDown() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("d1");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.BLACK));
		assertTrue(rook.canAttack(board, currentSquare, occupiedSquare));	
	}
	
	@Test
	public void testRookCannotAttackDiagonal() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e6");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.BLACK));
		assertFalse(rook.canAttack(board, currentSquare, occupiedSquare));	
	}
	
	@Test
	public void testCannotMoveToSameSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a1");
		board.placePiece(currentSquare, rook);
		assertFalse(rook.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackSameSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a1");
		board.placePiece(currentSquare, rook);
		assertFalse(rook.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveToOccupiedSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a2");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.WHITE));
		assertFalse(rook.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveWhenBlocked() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a8");
		board.placePiece(currentSquare, rook);
		board.placePiece(new Square("a4"), new Rook(Color.WHITE));
		assertFalse(rook.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackWhenBlocked() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a8");
		board.placePiece(currentSquare, rook);
		board.placePiece(occupiedSquare, new Rook(Color.BLACK));
		board.placePiece(new Square("a4"), new Rook(Color.WHITE));
		assertFalse(rook.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotAttackEmptySquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a2");
		board.placePiece(currentSquare, rook);
		assertFalse(rook.canAttack(board, currentSquare, emptySquare));
	}
	
}
