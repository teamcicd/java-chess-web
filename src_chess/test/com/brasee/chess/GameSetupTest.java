package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Piece.Color;
import com.brasee.chess.pieces.Piece.PieceType;

import static org.junit.Assert.*;

public class GameSetupTest {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testInitializeSetsUpCorrectBoard() {
		game.initializeBoard();
		
		// test white setup
		Piece whiteLeftRook = game.board().pieceOn(new Square("a1"));
		assertTrue(whiteLeftRook != null && whiteLeftRook.color().equals(Color.WHITE) && whiteLeftRook.pieceType().equals(PieceType.ROOK));
		Piece whiteLeftKnight = game.board().pieceOn(new Square("b1"));
		assertTrue(whiteLeftKnight != null && whiteLeftKnight.color().equals(Color.WHITE) && whiteLeftKnight.pieceType().equals(PieceType.KNIGHT));
		Piece whiteLeftBishop = game.board().pieceOn(new Square("c1"));
		assertTrue(whiteLeftBishop != null && whiteLeftBishop.color().equals(Color.WHITE) && whiteLeftBishop.pieceType().equals(PieceType.BISHOP));
		Piece whiteQueen = game.board().pieceOn(new Square("d1"));
		assertTrue(whiteQueen != null && whiteQueen.color().equals(Color.WHITE) && whiteQueen.pieceType().equals(PieceType.QUEEN));		
		Piece whiteKing = game.board().pieceOn(new Square("e1"));
		assertTrue(whiteKing != null && whiteKing.color().equals(Color.WHITE) && whiteKing.pieceType().equals(PieceType.KING));
		Piece whiteRightBishop = game.board().pieceOn(new Square("f1"));
		assertTrue(whiteRightBishop != null && whiteRightBishop.color().equals(Color.WHITE) && whiteRightBishop.pieceType().equals(PieceType.BISHOP));
		Piece whiteRightKnight = game.board().pieceOn(new Square("g1"));
		assertTrue(whiteRightKnight != null && whiteRightKnight.color().equals(Color.WHITE) && whiteRightKnight.pieceType().equals(PieceType.KNIGHT));
		Piece whiteRightRook = game.board().pieceOn(new Square("h1"));
		assertTrue(whiteRightRook != null && whiteRightRook.color().equals(Color.WHITE) && whiteRightRook.pieceType().equals(PieceType.ROOK));
		for (char row : new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}) {
			Piece pawn = game.board().pieceOn(new Square(row, '2'));
			assertTrue(pawn != null && pawn.color().equals(Color.WHITE) && pawn.pieceType().equals(PieceType.PAWN));
		}
		
		// test black setup
		Piece blackLeftRook = game.board().pieceOn(new Square("a8"));
		assertTrue(blackLeftRook != null && blackLeftRook.color().equals(Color.BLACK) && blackLeftRook.pieceType().equals(PieceType.ROOK));
		Piece blackLeftKnight = game.board().pieceOn(new Square("b8"));
		assertTrue(blackLeftKnight != null && blackLeftKnight.color().equals(Color.BLACK) && blackLeftKnight.pieceType().equals(PieceType.KNIGHT));
		Piece blackLeftBishop = game.board().pieceOn(new Square("c8"));
		assertTrue(blackLeftBishop != null && blackLeftBishop.color().equals(Color.BLACK) && blackLeftBishop.pieceType().equals(PieceType.BISHOP));
		Piece blackQueen = game.board().pieceOn(new Square("d8"));
		assertTrue(blackQueen != null && blackQueen.color().equals(Color.BLACK) && blackQueen.pieceType().equals(PieceType.QUEEN));		
		Piece blackKing = game.board().pieceOn(new Square("e8"));
		assertTrue(blackKing != null && blackKing.color().equals(Color.BLACK) && blackKing.pieceType().equals(PieceType.KING));
		Piece blackRightBishop = game.board().pieceOn(new Square("f8"));
		assertTrue(blackRightBishop != null && blackRightBishop.color().equals(Color.BLACK) && blackRightBishop.pieceType().equals(PieceType.BISHOP));
		Piece blackRightKnight = game.board().pieceOn(new Square("g8"));
		assertTrue(blackRightKnight != null && blackRightKnight.color().equals(Color.BLACK) && blackRightKnight.pieceType().equals(PieceType.KNIGHT));
		Piece blackRightRook = game.board().pieceOn(new Square("h8"));
		assertTrue(blackRightRook != null && blackRightRook.color().equals(Color.BLACK) && blackRightRook.pieceType().equals(PieceType.ROOK));
		for (char row : new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}) {
			Piece pawn = game.board().pieceOn(new Square(row, '7'));
			assertTrue(pawn != null && pawn.color().equals(Color.BLACK) && pawn.pieceType().equals(PieceType.PAWN));
		}
	}

}
