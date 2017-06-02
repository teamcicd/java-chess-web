package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;

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

import static org.junit.Assert.*;

public class GameAttackTest {

	private Game game;
	
	@Before
	public void setUp() {
		this.game = new Game();
	}
	
	@Test
	public void testPawnCanAttackValidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b3");
		Piece pawn = new Pawn(Color.WHITE);
		assertAttackSucceeds(pawn, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testRookCanAttackValidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("a7");
		Piece rook = new Rook(Color.WHITE);
		assertAttackSucceeds(rook, currentSquare, occupiedSquare);		
	}

	@Test
	public void testBishopCanAttackValidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("d5");
		Piece bishop = new Bishop(Color.WHITE);
		assertAttackSucceeds(bishop, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testKingCanAttackValidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b3");
		Piece king = new King(Color.WHITE);
		assertAttackSucceeds(king, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testQueenCanAttackValidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h2");
		Piece queen = new Queen(Color.WHITE);
		assertAttackSucceeds(queen, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testKnightCanAttackValidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b4");
		Piece knight = new Knight(Color.WHITE);
		assertAttackSucceeds(knight, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testPawnCannotAttackInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h8");
		Piece pawn = new Pawn(Color.WHITE);
		assertAttackFails(pawn, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testRookCannotAttackInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h8");
		Piece rook = new Rook(Color.WHITE);
		assertAttackFails(rook, currentSquare, occupiedSquare);		
	}

	@Test
	public void testBishopCannotAttackInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h8");
		Piece bishop = new Bishop(Color.WHITE);
		assertAttackFails(bishop, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testKingCannotAttackInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h8");
		Piece king = new King(Color.WHITE);
		assertAttackFails(king, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testQueenCannotAttackInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h8");
		Piece queen = new Queen(Color.WHITE);
		assertAttackFails(queen, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testKnightCannotAttackInvalidSquare() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("h8");
		Piece knight = new Knight(Color.WHITE);
		assertAttackFails(knight, currentSquare, occupiedSquare);		
	}
	
	@Test
	public void testIsFirstMoveIsFalseAfterPieceAttacks() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b3");
		Piece pawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(currentSquare, pawn);
		game.board().placePiece(occupiedSquare, blackPawn);
		game.move(currentSquare, occupiedSquare);
		assertFalse(pawn.isFirstMove());
	}
	
	@Test
	public void testAttackHistoryIsSavedForASingleMove() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b3");
		Piece pawn = new Pawn(Color.WHITE);
		assertAttackSucceeds(pawn, currentSquare, occupiedSquare);
		assertEquals(1, game.moves().size());
		Move lastMove = game.moves().get(0);
		assertSame(pawn, lastMove.piece());
		assertEquals(currentSquare, lastMove.startSquare());
		assertEquals(occupiedSquare, lastMove.endSquare());
	}
	
	@Test
	public void testAttackHistoryIsNotSavedForAnInvalidAttack() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b2");
		Piece pawn = new Pawn(Color.WHITE);
		game.board().placePiece(currentSquare, pawn);
		Piece enemyPiece = new Pawn(Color.BLACK);
		game.board().placePiece(occupiedSquare, enemyPiece);
		
		Move move = game.move(currentSquare, occupiedSquare);
		assertEquals(MoveType.INVALID, move.moveType());
		assertEquals(0, game.moves().size());
	}
	
	@Test
	public void testPawnCannotPerformNormalAttackToLastRow() {
		Square startSquare = new Square("a7");
		Square endSquare = new Square("b8");
		Piece pawn = new Pawn(Color.WHITE);
		Piece queen = new Queen(Color.BLACK);
		game.board().placePiece(startSquare, pawn);
		game.board().placePiece(endSquare, queen);
		assertEquals(MoveType.INVALID, game.move(startSquare, endSquare).moveType());
	}
	
	@Test
	public void testCaptureMoveReturnsCorrectClearedAndUpdatedSquares() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b3");
		Piece pawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(currentSquare, pawn);
		game.board().placePiece(occupiedSquare, blackPawn);
		Move move = game.move(currentSquare, occupiedSquare);
		
		assertEquals(1, move.clearedSquares().size());
		assertEquals(currentSquare, move.clearedSquares().get(0));
		assertEquals(1, move.updatedSquares().keySet().size());
		Square updatedSquare = move.updatedSquares().keySet().iterator().next();
		assertEquals(occupiedSquare, updatedSquare);
		assertEquals(pawn, move.updatedSquares().get(updatedSquare));
	}
	
	@Test
	public void testCaptureMoveCapturedPieceIsCorrect() {
		Square currentSquare = new Square("a2");
		Square occupiedSquare = new Square("b3");
		Piece pawn = new Pawn(Color.WHITE);
		Piece blackPawn = new Pawn(Color.BLACK);
		game.board().placePiece(currentSquare, pawn);
		game.board().placePiece(occupiedSquare, blackPawn);
		Move move = game.move(currentSquare, occupiedSquare);
		
		assertEquals(blackPawn, move.capturedPiece());
	}
	
	private void assertAttackSucceeds(Piece piece, Square currentSquare, Square occupiedSquare) {
		game.board().placePiece(currentSquare, piece);
		Piece enemyPiece = new Pawn(Color.BLACK);
		game.board().placePiece(occupiedSquare, enemyPiece);
		Move move = game.move(currentSquare, occupiedSquare);
		assertTrue(piece.equals(game.board().pieceOn(occupiedSquare)));
		assertTrue(game.capturedPieces(Color.BLACK).contains(enemyPiece));
		assertEquals(MoveType.CAPTURE, move.moveType());
		assertEquals(currentSquare, move.startSquare());
		assertEquals(occupiedSquare, move.endSquare());
		assertEquals(piece, move.piece());
	}
	
	private void assertAttackFails(Piece piece, Square currentSquare, Square occupiedSquare) {
		game.board().placePiece(currentSquare, piece);
		Piece enemyPiece = new Pawn(Color.BLACK);
		game.board().placePiece(occupiedSquare, enemyPiece);
		Move move = game.move(currentSquare, occupiedSquare);
		assertEquals(MoveType.INVALID, move.moveType());
		assertTrue(piece.equals(game.board().pieceOn(currentSquare)));
		assertTrue(enemyPiece.equals(game.board().pieceOn(occupiedSquare)));
		assertEquals(0, game.capturedPieces(Color.BLACK).size());
	}
	
}
