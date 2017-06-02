package com.brasee.chess.pieces;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class KnightTest {

	private Knight knight;
	private Board board;
	
	@Before
	public void setUp() {
		knight = new Knight(Color.WHITE);
		board = new Board();
	}
	
	@Test
	public void testCanMoveUp2Right1() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e7");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveUp2Left1() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c7");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDown2Right1() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("e3");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDown2Left1() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("c3");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveUp1Right2() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("f6");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveUp1Left2() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("b6");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDown1Right2() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("f4");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanMoveDown1Left2() {
		Square currentSquare = new Square("d5");
		Square emptySquare = new Square("b4");
		board.placePiece(currentSquare, knight);
		assertTrue(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCanAttackUp2Right1() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e7");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackUp2Left1() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c7");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDown2Right1() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("e3");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDown2Left1() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("c3");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackUp1Right2() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("f6");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackUp1Left2() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("b6");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDown1Right2() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("f4");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCanAttackDown1Left2() {
		Square currentSquare = new Square("d5");
		Square occupiedSquare = new Square("b4");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.BLACK));		
		assertTrue(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveVertically() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a3");
		board.placePiece(currentSquare, knight);
		assertFalse(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotMoveStraightHorizontally() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a3");
		board.placePiece(currentSquare, knight);
		assertFalse(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotMoveDiagonally() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("b2");
		board.placePiece(currentSquare, knight);
		assertFalse(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotMoveToSameSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a1");
		board.placePiece(currentSquare, knight);
		assertFalse(knight.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackSameSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a1");
		board.placePiece(currentSquare, knight);
		assertFalse(knight.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotMoveToOccupiedSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("b3");
		board.placePiece(currentSquare, knight);
		board.placePiece(occupiedSquare, new Knight(Color.WHITE));
		assertFalse(knight.canMove(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotAttackEmptySquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("b3");
		board.placePiece(currentSquare, knight);
		assertFalse(knight.canAttack(board, currentSquare, emptySquare));
	}
}
