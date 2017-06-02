package com.brasee.chess.pieces;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class PawnTest {

	private Piece whitePawn;
	private Piece blackPawn;
	private Board board;
	
	@Before
	public void setUp() {
		whitePawn = new Pawn(Color.WHITE);
		blackPawn = new Pawn(Color.BLACK);
		board = new Board();
	}
	
	@Test
	public void testWhitePawnCanMoveUpOneSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		board.placePiece(currentSquare, whitePawn);
		assertTrue(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testWhitePawnCannotMoveDown() {
		Square currentSquare = new Square("a7");
		Square emptySquare = new Square("a6");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testWhitePawnCanMoveUpTwoSquaresIfThisIsFirstMove() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a4");
		board.placePiece(currentSquare, whitePawn);
		assertTrue(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testWhitePawnCannotMoveUpTwoSquaresOnFirstMoveIfBlocked() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a4");
		Square occupiedSquare = new Square("a3");
		board.placePiece(currentSquare, whitePawn);
		board.placePiece(occupiedSquare, blackPawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testWhitePawnCannotMoveUpThreeSquares() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a5");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testWhitePawnCanAttackPieceUpAndLeftOneSquare() {
		Square currentSquare = new Square("b2");
		Square occupiedSquare = new Square("c3");
		board.placePiece(currentSquare, whitePawn);
		board.placePiece(occupiedSquare, blackPawn);
		assertTrue(whitePawn.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testWhitePawnCanAttackPieceUpAndRightOneSquare() {
		Square currentSquare = new Square("b2");
		Square occupiedSquare = new Square("a3");
		board.placePiece(currentSquare, whitePawn);
		board.placePiece(occupiedSquare, blackPawn);
		assertTrue(whitePawn.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testBlackPawnCanMoveDownOneSquare() {
		Square currentSquare = new Square("a7");
		Square emptySquare = new Square("a6");
		board.placePiece(currentSquare, blackPawn);
		assertTrue(blackPawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testBlackPawnCannotMoveUp() {
		Square currentSquare = new Square("a7");
		Square emptySquare = new Square("a8");
		board.placePiece(currentSquare, blackPawn);
		assertFalse(blackPawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testBlackPawnCanMoveDownTwoSquaresIfThisIsFirstMove() {
		Square currentSquare = new Square("a7");
		Square emptySquare = new Square("a5");
		board.placePiece(currentSquare, blackPawn);
		assertTrue(blackPawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test 
	public void testBlackPawnCannotMoveDownThreeSquares() {
		Square currentSquare = new Square("a7");
		Square emptySquare = new Square("a4");
		board.placePiece(currentSquare, blackPawn);
		assertFalse(blackPawn.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testBlackPawnCanAttackDownAndLeftOneSquare() {
		Square currentSquare = new Square("b7");
		Square occupiedSquare = new Square("a6");
		board.placePiece(currentSquare, blackPawn);
		board.placePiece(occupiedSquare, whitePawn);
		assertTrue(blackPawn.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testBlackPawnCanAttackDownAndRightOneSquare() {
		Square currentSquare = new Square("b7");
		Square occupiedSquare = new Square("c6");
		board.placePiece(currentSquare, blackPawn);
		board.placePiece(occupiedSquare, whitePawn);
		assertTrue(blackPawn.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testPawnCannotMoveTwoSquaresTwice() {
		Square currentSquare = new Square("a4");
		Square emptySquare = new Square("a6");
		whitePawn.incrementTimesMoved();
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testPawnCannotMoveSideways() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("b2");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testPawnCannotMoveDiagonally() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("b3");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));		
	}
	
	@Test
	public void testPawnCannotAttackForward() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("a3");
		board.placePiece(currentSquare, whitePawn);
		board.placePiece(occupiedSquare, blackPawn);
		assertFalse(whitePawn.canAttack(board, currentSquare, occupiedSquare));		
	}

	@Test
	public void testPawnCannotAttackSideways() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b2");
		board.placePiece(currentSquare, whitePawn);
		board.placePiece(occupiedSquare, blackPawn);
		assertFalse(whitePawn.canAttack(board, currentSquare, occupiedSquare));		
	}
	
	@Test
	public void testCannotMoveToSameSquare() {
		Square currentSquare = new Square("a1");
		Square emptySquare = new Square("a1");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canMove(board, currentSquare, emptySquare));
	}
	
	@Test
	public void testCannotAttackSameSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a1");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canAttack(board, currentSquare, occupiedSquare));
	}
	
	@Test
	public void testCannotAttackEmptySquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("b2");
		board.placePiece(currentSquare, whitePawn);
		assertFalse(whitePawn.canAttack(board, currentSquare, occupiedSquare));		
	}
	
	@Test
	public void testCannotMoveToOccupiedSquare() {
		Square currentSquare = new Square("a1");
		Square occupiedSquare = new Square("a2");
		board.placePiece(currentSquare, whitePawn);
		board.placePiece(occupiedSquare, blackPawn);
		assertFalse(whitePawn.canMove(board, currentSquare, occupiedSquare));		
	}

}
