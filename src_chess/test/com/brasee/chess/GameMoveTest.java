package com.brasee.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.moves.InvalidMove;
import com.brasee.chess.moves.Move;
import com.brasee.chess.moves.Move.MoveType;
import com.brasee.chess.pieces.Bishop;
import com.brasee.chess.pieces.King;
import com.brasee.chess.pieces.Knight;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Queen;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;

public class GameMoveTest {

	private Game game;
	
	@Before
	public void setUp() {
		this.game = new Game();
	}
	
	@Test
	public void testInvalidMovesCannotBeExecuted() {
		assertFalse(InvalidMove.canBeExecuted());
	}
	
	@Test
	public void testPawnCanMoveToValidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece pawn = new Pawn(Color.WHITE);
		assertMoveSucceeds(pawn, currentSquare, emptySquare);
	}
	
	@Test
	public void testRookCanMoveToValidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a6");
		Piece rook = new Rook(Color.WHITE);
		assertMoveSucceeds(rook, currentSquare, emptySquare);
	}
	
	@Test
	public void testBishopCanMoveToValidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("d5");
		Piece bishop = new Bishop(Color.WHITE);
		assertMoveSucceeds(bishop, currentSquare, emptySquare);
	}
	
	@Test
	public void testQueenCanMoveToValidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a6");
		Piece queen = new Queen(Color.WHITE);
		assertMoveSucceeds(queen, currentSquare, emptySquare);
	}
	
	@Test
	public void testKingCanMoveToValidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece king = new King(Color.WHITE);
		assertMoveSucceeds(king, currentSquare, emptySquare);
	}
	
	@Test
	public void testKnightCanMoveToValidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("b4");
		Piece knight = new Knight(Color.WHITE);
		assertMoveSucceeds(knight, currentSquare, emptySquare);
	}
	
	@Test
	public void testPawnCannotMoveToInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("h1");
		Piece pawn = new Pawn(Color.WHITE);
		assertMoveFails(pawn, currentSquare, emptySquare);
	}
	
	@Test
	public void testRookCannotMoveToInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("h1");
		Piece rook = new Rook(Color.WHITE);
		assertMoveFails(rook, currentSquare, emptySquare);
	}
	
	@Test
	public void testBishopCannotMoveToInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("h1");
		Piece bishop = new Bishop(Color.WHITE);
		assertMoveFails(bishop, currentSquare, emptySquare);
	}
	
	@Test
	public void testQueenCannotMoveToInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("h1");
		Piece queen = new Queen(Color.WHITE);
		assertMoveFails(queen, currentSquare, emptySquare);
	}
	
	@Test
	public void testKingCannotMoveToInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("h1");
		Piece king = new King(Color.WHITE);
		assertMoveFails(king, currentSquare, emptySquare);
	}
	
	@Test
	public void testKnightCannotMoveToInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("h1");
		Piece knight = new Knight(Color.WHITE);
		assertMoveFails(knight, currentSquare, emptySquare);
	}
	
	@Test
	public void testIsFirstMoveIsFalseAfterPieceMoves() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece pawn = new Pawn(Color.WHITE);
		game.board().placePiece(currentSquare, pawn);
		game.move(currentSquare, emptySquare);
		assertFalse(pawn.isFirstMove());
	}
	
	@Test
	public void testMoveHistoryIsSavedForASingleMove() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece pawn = new Pawn(Color.WHITE);
		assertMoveSucceeds(pawn, currentSquare, emptySquare);
		assertEquals(1, game.moves().size());
		Move lastMove = game.moves().get(0);
		assertSame(pawn, lastMove.piece());
		assertEquals(currentSquare, lastMove.startSquare());
		assertEquals(emptySquare, lastMove.endSquare());
	}
	
	@Test
	public void testMoveHistoryIsSavedForMultipleMoves() {
		game.initializeBoard();
		
		// move white pawn
		Square whitePawnStart = new Square("a2");
		Square whitePawnEnd = new Square("a3");
		Piece whitePawn = game.board().pieceOn(whitePawnStart);
		game.move(whitePawnStart, whitePawnEnd);
		// move black pawn
		Square blackPawnStart = new Square("a7");
		Square blackPawnEnd = new Square("a6");
		Piece blackPawn = game.board().pieceOn(blackPawnStart);
		game.move(blackPawnStart, blackPawnEnd);
		// move white knight
		Square whiteKnightStart = new Square("b1");
		Square whiteKnightEnd = new Square("c3");
		Piece whiteKnight = game.board().pieceOn(whiteKnightStart);
		game.move(whiteKnightStart, whiteKnightEnd);
		
		assertEquals(3, game.moves().size());
		assertMoveEquals(game.moves().get(0), MoveType.NORMAL, whitePawn, whitePawnStart, whitePawnEnd);
		assertMoveEquals(game.moves().get(1), MoveType.NORMAL, blackPawn, blackPawnStart, blackPawnEnd);
		assertMoveEquals(game.moves().get(2), MoveType.NORMAL, whiteKnight, whiteKnightStart, whiteKnightEnd);
	}
	
	@Test
	public void testMoveHistoryIsNotSavedForAnInvalidMove() {
		game.initializeBoard();
		
		// try to perform an invalid move (no piece to move)
		game.move(new Square("d4"), new Square("d5"));
		
		assertEquals(0, game.moves().size());
	}
	
	@Test
	public void testLastMoveIsNullIfNoMovesHaveBeenMade() {
		game.initializeBoard();
		assertNull(game.lastMove());
	}
	
	@Test
	public void testLastMoveIsReturnedIfAMoveHasBeenMade() {
		game.initializeBoard();
		Square startSquare = new Square("a2");
		Square endSquare = new Square("a3");
		Piece piece = game.board().pieceOn(startSquare);
		Move move = game.move(startSquare, endSquare);
		assertSame(move, game.lastMove());
		assertSame(piece, move.piece());
		assertEquals(startSquare, move.startSquare());
		assertEquals(endSquare, move.endSquare());
	}
	
	@Test
	public void testPawnCannotPerformNormalMoveToLastRow() {
		Square startSquare = new Square("a7");
		Square endSquare = new Square("a8");
		Piece pawn = new Pawn(Color.WHITE);
		game.board().placePiece(startSquare, pawn);
		assertEquals(MoveType.INVALID, game.move(startSquare, endSquare).moveType());
	}
	
	@Test
	public void testNormalMoveReturnsCorrectClearedAndUpdatedSquares() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece pawn = new Pawn(Color.WHITE);
		game.board().placePiece(currentSquare, pawn);
		Move move = game.move(currentSquare, emptySquare);
		assertEquals(1, move.clearedSquares().size());
		assertEquals(currentSquare, move.clearedSquares().get(0));
		assertEquals(1, move.updatedSquares().keySet().size());
		Square updatedSquare = move.updatedSquares().keySet().iterator().next();
		assertEquals(emptySquare, updatedSquare);
		assertEquals(pawn, move.updatedSquares().get(updatedSquare));
	}
	
	@Test
	public void testNormalMoveCapturedPieceIsNull() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece pawn = new Pawn(Color.WHITE);
		game.board().placePiece(currentSquare, pawn);
		Move move = game.move(currentSquare, emptySquare);
		assertNull(move.capturedPiece());
	}
	
	@Test
	public void testMoveNotationConsistsOfStartAndEndSquares() {
		Square currentSquare = new Square("a2");
		Square emptySquare = new Square("a3");
		Piece pawn = new Pawn(Color.WHITE);
		game.board().placePiece(currentSquare, pawn);
		Move move = game.move(currentSquare, emptySquare);
		assertEquals("a2.a3", move.notation());
	}

		
	private void assertMoveEquals(Move move, MoveType normal, Piece piece, Square startSquare, Square endSquare) {
		assertEquals(piece, move.piece());
		assertEquals(MoveType.NORMAL, move.moveType());
		assertEquals(startSquare, move.startSquare());
		assertEquals(endSquare, move.endSquare());
	}

	private void assertMoveSucceeds(Piece piece, Square currentSquare, Square emptySquare) {
		game.board().placePiece(currentSquare, piece);
		Move move = game.move(currentSquare, emptySquare);
		assertTrue(!game.board().hasPieceOn(currentSquare));
		assertTrue(piece.equals(game.board().pieceOn(emptySquare)));
		assertEquals(MoveType.NORMAL, move.moveType());
		assertEquals(currentSquare, move.startSquare());
		assertEquals(emptySquare, move.endSquare());
		assertEquals(piece, move.piece());		
	}
	
	private void assertMoveFails(Piece piece, Square currentSquare, Square emptySquare) {
		game.board().placePiece(currentSquare, piece);
		Move move = game.move(currentSquare, emptySquare);
		assertEquals(MoveType.INVALID, move.moveType());
		assertTrue(piece.equals(game.board().pieceOn(currentSquare)));
		assertTrue(!game.board().hasPieceOn(emptySquare));
	}
	
}
