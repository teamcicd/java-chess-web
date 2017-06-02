package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.brasee.chess.pieces.Bishop;
import com.brasee.chess.pieces.King;
import com.brasee.chess.pieces.Knight;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Queen;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;

public class BoardCheckTest {

	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
	}

	@Test
	public void testNoPiecesDoesNotCauseCheck() {
		assertFalse(board.inCheck(Color.WHITE));
		assertFalse(board.inCheck(Color.BLACK));
	}
	
	@Test
	public void testNoOpposingPiecesDoesNotCauseCheck() {
		board.placePiece(new Square("a2"), new King(Color.WHITE));
		assertFalse(board.inCheck(Color.WHITE));
	}

	@Test
	public void testRookBlockedDoesNotCauseCheck() {
		board.placePiece(new Square("a2"), new King(Color.WHITE));
		board.placePiece(new Square("a5"), new Pawn(Color.WHITE));
		board.placePiece(new Square("a8"), new Rook(Color.BLACK));
		assertFalse(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testRookWithRankPathCausesCheck() {
		board.placePiece(new Square("a2"), new King(Color.WHITE));
		board.placePiece(new Square("f2"), new Rook(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testRookWithFilePathCausesCheck() {
		board.placePiece(new Square("a2"), new King(Color.WHITE));
		board.placePiece(new Square("a8"), new Rook(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testBishopWithPathCausesCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("h8"), new Bishop(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testBishopBlockedDoesNotCauseCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("b2"), new Pawn(Color.WHITE));
		board.placePiece(new Square("h8"), new Bishop(Color.BLACK));
		assertFalse(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testQueenWithPathCausesCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("h8"), new Queen(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testQueenBlockedDoesNotCauseCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("b2"), new Pawn(Color.WHITE));
		board.placePiece(new Square("h8"), new Queen(Color.BLACK));
		assertFalse(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testKingWithPathCausesCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("a2"), new King(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testKnightWithPathCausesCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("b3"), new Knight(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testKnightBlockedStillCausesCheck() {
		board.placePiece(new Square("a1"), new King(Color.WHITE));
		board.placePiece(new Square("a2"), new Pawn(Color.WHITE));
		board.placePiece(new Square("a3"), new Pawn(Color.WHITE));
		board.placePiece(new Square("b3"), new Knight(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testPawnWithPathCausesCheck() {
		board.placePiece(new Square("d4"), new King(Color.WHITE));
		board.placePiece(new Square("e5"), new Pawn(Color.BLACK));
		assertTrue(board.inCheck(Color.WHITE));
	}
	
	@Test
	public void testWhitePieceCausesBlackKingInCheck() {
		board.placePiece(new Square("a2"), new Rook(Color.WHITE));
		board.placePiece(new Square("a8"), new King(Color.BLACK));
		assertTrue(board.inCheck(Color.BLACK));
	}
	
}
