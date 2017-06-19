package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class BoardTest {

	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
	}
	
	@Test
	public void testCanCheckForAPieceAtPosition() {
		Piece pawn = new Pawn(Color.WHITE);
		Square square = new Square("a2");
		board.placePiece(square, pawn);
		assertFalse(board.hasPieceOn(square));
	}
	
	@Test
	public void testCanCheckForNoPieceAtPosition() {
		Piece pawn = new Pawn(Color.WHITE);
		Square pawnSquare = new Square("a2");
		Square emptySquare =  new Square("a3");
		board.placePiece(pawnSquare, pawn);
		assertFalse(board.hasPieceOn(emptySquare));
	}
	
	@Test
	public void testCanCheckForWhichPieceIsAtPosition() {
		Piece pawn = new Pawn(Color.WHITE);
		Square square = new Square("a2");
		board.placePiece(square, pawn);
		assertTrue(pawn.equals(board.pieceOn(square)));
	}
	
	@Test
	public void testThrowsExceptionIfPiecePlacedAtAnOccupiedPosition() {
		Piece pawn1 = new Pawn(Color.WHITE);
		Piece pawn2 = new Pawn(Color.WHITE);
		Square square = new Square("a2");
		board.placePiece(square, pawn1);
		try {
			board.placePiece(square, pawn2);
		}
		catch (SquareAlreadyOccupiedException e) { 
			return;
		}
		fail("Should throw a SquareAlreadyOccupiedException");
	}
	
	@Test
	public void testMoveRemovesAPieceFromASquare() {
		Piece pawn = new Pawn(Color.WHITE);
		Square startSquare = new Square("a2");
		Square endSquare = new Square("a3");
		board.placePiece(startSquare, pawn);
		board.movePiece(pawn, startSquare, endSquare);
		assertFalse(board.hasPieceOn(startSquare));
	}
	
	@Test
	public void testMoveOccupiesASquare() {
		Piece pawn = new Pawn(Color.WHITE);
		Square startSquare = new Square("a2");
		Square endSquare = new Square("a3");
		board.placePiece(startSquare, pawn);
		board.movePiece(pawn, startSquare, endSquare);
		assertTrue(board.hasPieceOn(endSquare));
	}
	
	@Test
	public void testMoveMovesAPieceToASquare() {
		Piece pawn = new Pawn(Color.WHITE);
		Square startSquare = new Square("a2");
		Square endSquare = new Square("a3");
		board.placePiece(startSquare, pawn);
		board.movePiece(pawn, startSquare, endSquare);
		assertTrue(pawn.equals(board.pieceOn(endSquare)));
	}
	
	@Test
	public void testClearPathInFileOneSquareApartLowToHigh() {
		Square square1 = new Square("a1");
		Square square2 = new Square("a2");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testClearPathInFileOneSquareApartHighToLow() {
		Square square1 = new Square("a2");
		Square square2 = new Square("a1");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testClearPathInFileMultipleSquaresApartLowToHigh() {
		Square square1 = new Square("a1");
		Square square2 = new Square("a8");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testClearPathInFileMultipleSquaresApartHighToLow() {
		Square square1 = new Square("a8");
		Square square2 = new Square("a1");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testNotClearPathInFileWithBlockingPiece() {
		Square square1 = new Square("a1");
		Square square2 = new Square("a3");
		board.placePiece(new Square("a2"), new Pawn(Color.WHITE));
		assertFalse(board.clearPathBetween(square1, square2));
	}

	@Test
	public void testClearPathInRankOneSquareApartLowToHigh() {
		Square square1 = new Square("a1");
		Square square2 = new Square("b1");
		assertTrue(board.clearPathBetween(square1, square2));
	}

	@Test
	public void testClearPathInRankOneSquareApartHighToLow() {
		Square square1 = new Square("b1");
		Square square2 = new Square("a1");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testClearPathInRankMultipleSquaresApartLowToHigh() {
		Square square1 = new Square("a1");
		Square square2 = new Square("h1");
		assertTrue(board.clearPathBetween(square1, square2));
	}

	@Test
	public void testClearPathInRankMultipleSquaresApartHighToLow() {
		Square square1 = new Square("h1");
		Square square2 = new Square("a1");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testNotClearPathInRankWithBlockingPiece() {
		Square square1 = new Square("a1");
		Square square2 = new Square("c1");
		board.placePiece(new Square("b1"), new Pawn(Color.WHITE));
		assertFalse(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testDiagonalClearPathBottomLeftToTopRight() {
		Square square1 = new Square("a1");
		Square square2 = new Square("h8");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testDiagonalClearPathTopRightToBottomLeft() {
		Square square1 = new Square("h8");
		Square square2 = new Square("a1");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testDiagonalClearPathBottomRightToTopLeft() {
		Square square1 = new Square("a8");
		Square square2 = new Square("h1");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testDiagonalClearPathTopLeftToBottomRight() {
		Square square1 = new Square("h1");
		Square square2 = new Square("a8");
		assertTrue(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testDiagonalPathNotClearWithBlockingPiece() {
		Square square1 = new Square("a1");
		Square square2 = new Square("c3");
		board.placePiece(new Square("b2"), new Pawn(Color.WHITE));
		assertFalse(board.clearPathBetween(square1, square2));
	}
	
	@Test
	public void testSameSquareDoesNotHaveAClearPath() {
		Square square = new Square("a1");
		board.placePiece(square, new Pawn(Color.WHITE));
		assertFalse(board.clearPathBetween(square, square));
	}
	
}
