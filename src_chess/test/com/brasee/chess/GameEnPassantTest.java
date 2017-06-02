package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.moves.Move;
import com.brasee.chess.moves.Move.MoveType;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class GameEnPassantTest {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testWhitePawnCanEnPassantToLeft() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(new Square("e4"), whitePawn);
		game.board().placePiece(new Square("d7"), blackPawn);
		Square enPassantStartSquare = new Square("e5");
		Square enPassantEndSquare = new Square("d6");
		// white pawn move
		game.move(new Square("e4"), enPassantStartSquare);
		// black pawn move
		game.move(new Square("d7"), new Square("d5"));
		
		// perform en passant
		assertEnPassantSucceeded(whitePawn, enPassantStartSquare, enPassantEndSquare, blackPawn);
	}
	
	@Test
	public void testWhitePawnCanEnPassantToRight() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(new Square("e4"), whitePawn);
		game.board().placePiece(new Square("f7"), blackPawn);
		Square enPassantStartSquare = new Square("e5");
		Square enPassantEndSquare = new Square("f6");
		// white pawn move
		game.move(new Square("e4"), enPassantStartSquare);
		// black pawn move
		game.move(new Square("f7"), new Square("f5"));
		
		// perform en passant
		assertEnPassantSucceeded(whitePawn, enPassantStartSquare, enPassantEndSquare, blackPawn);
	}
	
	@Test
	public void testBlackPawnCanEnPassantToLeft() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		Square enPassantStartSquare = new Square("e4");
		Square enPassantEndSquare = new Square("d3");
		game.board().placePiece(enPassantStartSquare, blackPawn);
		game.board().placePiece(new Square("d2"), whitePawn);

		// white pawn move
		game.move(new Square("d2"), new Square("d4"));
		
		// perform en passant
		assertEnPassantSucceeded(blackPawn, enPassantStartSquare, enPassantEndSquare, whitePawn);
	}
	
	@Test
	public void testBlackPawnCanEnPassantToRight() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		Square enPassantStartSquare = new Square("e4");
		Square enPassantEndSquare = new Square("f3");
		game.board().placePiece(enPassantStartSquare, blackPawn);
		game.board().placePiece(new Square("f2"), whitePawn);

		// white pawn move
		game.move(new Square("f2"), new Square("f4"));
		
		// perform en passant
		assertEnPassantSucceeded(blackPawn, enPassantStartSquare, enPassantEndSquare, whitePawn);
	}
	
	@Test
	public void testPawnCannotEnPassantIfLastMoveWasNot2Squares() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		Square enPassantStartSquare = new Square("e4");
		Square enPassantEndSquare = new Square("f3");
		game.board().placePiece(enPassantStartSquare, blackPawn);
		game.board().placePiece(new Square("f2"), whitePawn);

		// setup unimportant pieces
		game.board().placePiece(new Square("a7"), new Pawn(Color.BLACK));
		game.board().placePiece(new Square("a2"), new Pawn(Color.WHITE));
		
		// white pawn move
		game.move(new Square("f2"), new Square("f4"));
		
		// move a couple random pawns
		game.move(new Square("a7"), new Square("a6"));
		game.move(new Square("a2"), new Square("a3"));
		
		// verify en passant doesn't work
		assertEquals(MoveType.INVALID, game.move(enPassantStartSquare, enPassantEndSquare).moveType());
	}
	
	@Test
	public void testEnPassantReturnsCorrectClearedAndUpdatedSquares() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(new Square("e4"), whitePawn);
		game.board().placePiece(new Square("d7"), blackPawn);
		Square enPassantStartSquare = new Square("e5");
		Square enPassantEndSquare = new Square("d6");
		// white pawn move
		game.move(new Square("e4"), enPassantStartSquare);
		// black pawn move
		game.move(new Square("d7"), new Square("d5"));
		
		Move move = game.move(enPassantStartSquare, enPassantEndSquare);
		assertEquals(2, move.clearedSquares().size());
		assertTrue(move.clearedSquares().contains(enPassantStartSquare));
		assertTrue(move.clearedSquares().contains(new Square("d5")));
		assertEquals(1, move.updatedSquares().keySet().size());
		Square updatedSquare = move.updatedSquares().keySet().iterator().next();
		assertEquals(enPassantEndSquare, updatedSquare);
		assertEquals(whitePawn, move.updatedSquares().get(updatedSquare));
	}

	@Test
	public void testEnPassantCapturedPieceIsCorrect() {
		// setup en passant
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(new Square("e4"), whitePawn);
		game.board().placePiece(new Square("d7"), blackPawn);
		Square enPassantStartSquare = new Square("e5");
		Square enPassantEndSquare = new Square("d6");
		// white pawn move
		game.move(new Square("e4"), enPassantStartSquare);
		// black pawn move
		game.move(new Square("d7"), new Square("d5"));
		
		Move move = game.move(enPassantStartSquare, enPassantEndSquare);
		assertEquals(blackPawn, move.capturedPiece());
	}
	
	private void assertEnPassantSucceeded(Piece pawn, Square startSquare, Square endSquare, Piece opposingPawn) {
		Move move = game.move(startSquare, endSquare);		
		assertEquals(pawn, game.board().pieceOn(endSquare));
		assertFalse(game.board().hasPieceOn(new Square(endSquare.file(), startSquare.rank())));
		assertEquals(MoveType.EN_PASSANT, move.moveType());
		assertEquals(startSquare, move.startSquare());
		assertEquals(endSquare, move.endSquare());
		assertEquals(pawn, move.piece());
		assertEquals(1, game.capturedPieces(opposingPawn.color()).size());
		assertTrue(game.capturedPieces(opposingPawn.color()).contains(opposingPawn));		
	}

}
