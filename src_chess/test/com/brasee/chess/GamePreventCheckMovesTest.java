package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.moves.Move;
import com.brasee.chess.moves.Move.MoveType;
import com.brasee.chess.pieces.King;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Queen;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class GamePreventCheckMovesTest {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}

	@Test
	public void testAllowMoveThatWillResultInCheckForOtherPlayer() {
		Piece blackKing = new King(Color.BLACK);
		Piece whiteRook = new Rook(Color.WHITE);
		game.board().placePiece(new Square("a1"), blackKing);
		game.board().placePiece(new Square("f2"), whiteRook);
		assertFalse(game.board().inCheck(Color.BLACK));
		
		Move move = game.move(new Square("f2"), new Square("f1"));
		assertEquals(MoveType.NORMAL, move.moveType());
		assertTrue(game.board().inCheck(Color.BLACK));
	}
	
	@Test
	public void testPreventAndUndoNormalMoveThatWillResultInCheckForCurrentPlayer() {
		Piece whiteKing = new King(Color.WHITE);
		Piece blackRook = new Rook(Color.BLACK);
		game.board().placePiece(new Square("a1"), whiteKing);
		game.board().placePiece(new Square("f2"), blackRook);
		assertFalse(game.board().inCheck(Color.WHITE));
		
		Move move = game.move(new Square("a1"), new Square("a2"));
		assertEquals(MoveType.INVALID, move.moveType());
		assertFalse(game.board().inCheck(Color.WHITE));
		assertTrue(whiteKing.isFirstMove());
		assertEquals(whiteKing, game.board().pieceOn(new Square("a1")));
		assertEquals(blackRook, game.board().pieceOn(new Square("f2")));
		assertNull(game.board().pieceOn(new Square("a2")));
	}
		
	@Test
	public void testPreventAndUndoCaptureMoveThatWillResultInCheckForCurrentPlayer() {
		Piece whiteKing = new King(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		Piece blackQueen = new Queen(Color.BLACK);
		
		game.board().placePiece(new Square("a1"), whiteKing);
		game.board().placePiece(new Square("a2"), blackPawn);
		game.board().placePiece(new Square("a8"), blackQueen);
		assertFalse(game.board().inCheck(Color.WHITE));
		
		Move move = game.move(new Square("a1"), new Square("a2"));
		assertEquals(MoveType.INVALID, move.moveType());
		assertFalse(game.board().inCheck(Color.WHITE));
		assertTrue(whiteKing.isFirstMove());
		assertEquals(whiteKing, game.board().pieceOn(new Square("a1")));
		assertEquals(blackPawn, game.board().pieceOn(new Square("a2")));
		assertTrue(game.capturedPieces(Color.BLACK).isEmpty());
	}
	
	@Test
	public void testPreventAndUndoEnPassantMoveThatWillResultInCheckForCurrentPlayer() {
		Piece whiteKing = new King(Color.WHITE);
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		Piece blackQueen = new Queen(Color.BLACK);
		
		game.board().placePiece(new Square("a1"), whiteKing);
		game.board().placePiece(new Square("a5"), whitePawn);
		game.board().placePiece(new Square("b7"), blackPawn);
		game.board().placePiece(new Square("a8"), blackQueen);
		assertFalse(game.board().inCheck(Color.WHITE));
		
		// set up en passant
		game.move(new Square("a1"), new Square("a2"));
		game.move(new Square("b7"), new Square("b5"));
		
		Move move = game.move(new Square("a5"), new Square("b6"));
		assertEquals(MoveType.INVALID, move.moveType());
		assertFalse(game.board().inCheck(Color.WHITE));
		assertTrue(whitePawn.isFirstMove());
		assertFalse(blackPawn.isFirstMove());
		assertEquals(whitePawn, game.board().pieceOn(new Square("a5")));
		assertEquals(blackPawn, game.board().pieceOn(new Square("b5")));
		assertTrue(game.capturedPieces(Color.BLACK).isEmpty());
	}
	
	@Test
	public void testPreventCastlingMoveThatWillResultInCheckForCurrentPlayer() {
		Piece whiteRook = new Rook(Color.WHITE);
		Piece whiteKing = new King(Color.WHITE);
		Piece blackQueen = new Queen(Color.BLACK);
		
		game.board().placePiece(new Square("e1"), whiteKing);
		game.board().placePiece(new Square("h1"), whiteRook);
		game.board().placePiece(new Square("g8"), blackQueen);
		
		Move move = game.move(new Square("e1"), new Square("g1"));
		assertEquals(MoveType.INVALID, move.moveType());
		assertEquals(whiteKing, game.board().pieceOn(new Square("e1")));
		assertEquals(whiteRook, game.board().pieceOn(new Square("h1")));
		assertTrue(whiteKing.isFirstMove());
		assertTrue(whiteRook.isFirstMove());
	}
	
	@Test
	public void testPreventCastlingMoveIfKingInCurrentlyInCheck() {
		Piece whiteRook = new Rook(Color.WHITE);
		Piece whiteKing = new King(Color.WHITE);
		Piece blackQueen = new Queen(Color.BLACK);
		
		game.board().placePiece(new Square("e1"), whiteKing);
		game.board().placePiece(new Square("h1"), whiteRook);
		game.board().placePiece(new Square("e8"), blackQueen);
		
		Move move = game.move(new Square("e1"), new Square("g1"));
		assertEquals(MoveType.INVALID, move.moveType());
		assertEquals(whiteKing, game.board().pieceOn(new Square("e1")));
		assertEquals(whiteRook, game.board().pieceOn(new Square("h1")));
		assertTrue(whiteKing.isFirstMove());
		assertTrue(whiteRook.isFirstMove());
	}
	
	@Test
	public void testPreventCastlingMoveIfKingPassesThroughCheck() {
		Piece whiteRook = new Rook(Color.WHITE);
		Piece whiteKing = new King(Color.WHITE);
		Piece blackQueen = new Queen(Color.BLACK);
		
		game.board().placePiece(new Square("e1"), whiteKing);
		game.board().placePiece(new Square("h1"), whiteRook);
		game.board().placePiece(new Square("f8"), blackQueen);
		
		Move move = game.move(new Square("e1"), new Square("g1"));
		assertEquals(MoveType.INVALID, move.moveType());
		assertEquals(whiteKing, game.board().pieceOn(new Square("e1")));
		assertEquals(whiteRook, game.board().pieceOn(new Square("h1")));
		assertTrue(whiteKing.isFirstMove());
		assertTrue(whiteRook.isFirstMove());
	}
	
}
