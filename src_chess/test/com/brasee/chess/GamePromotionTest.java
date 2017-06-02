package com.brasee.chess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.moves.Move;
import com.brasee.chess.moves.PromotionMove;
import com.brasee.chess.moves.Move.MoveType;
import com.brasee.chess.pieces.King;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;
import com.brasee.chess.pieces.Piece.PieceType;

public class GamePromotionTest {

	private Game game;

	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testSuccessfulPromotionReturnsCorrectMoveType() {
		Move move = performSimplePromotion();
		assertEquals(MoveType.PROMOTION, move.moveType());
	}
	
	@Test
	public void testSuccessfulPromotionChangesTurn() {
		performSimplePromotion();
		assertEquals(Color.BLACK, game.playersTurn());
	}
	
	@Test
	public void testSuccessfulPromotionAddsCorrectPiece() {
		performSimplePromotion();
		assertEquals(PieceType.QUEEN, game.board().pieceOn(new Square("a8")).pieceType());
		assertEquals(Color.WHITE, game.board().pieceOn(new Square("a8")).color());
	}
	
	@Test
	public void testSuccessfulPromotionStoresPawnUsedForPromotion() {
		Piece whitePawn = new Pawn(Color.WHITE);
		Move move = performSimplePromotion(whitePawn);
		assertEquals(whitePawn, move.piece());
	}
	
	@Test
	public void testSuccessfulPromotionReplacesPawnWithChosenPiece() {
		PromotionMove move = (PromotionMove) performSimplePromotion();
		assertEquals(PieceType.QUEEN, game.board().pieceOn(new Square("a8")).pieceType());
		assertSame(game.board().pieceOn(new Square("a8")), move.promotionPiece());
	}
	
	@Test
	public void testSuccessfulPromotionSavedInGameMoves() {
		Move move = performSimplePromotion();
		assertEquals(1, game.moves().size());
		assertTrue(game.moves().contains(move));
	}
	
	@Test
	public void testPromotionCanBePerformedByCapture() {
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackRook = new Rook(Color.BLACK);
		game.board().placePiece(new Square("a7"), whitePawn);
		game.board().placePiece(new Square("b8"), blackRook);
		
		Move move = game.promote(new Square("a7"), new Square("b8"), PieceType.QUEEN);
		assertEquals(MoveType.PROMOTION, move.moveType());
	}
	
	@Test
	public void testSuccessfulMovePromotionHasNullOpposingPiece() {
		PromotionMove move = (PromotionMove) performSimplePromotion();
		assertNull(move.opposingPiece());
	}

	@Test
	public void testSuccessfulAttackPromotionSavesCapturedPiece() {
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackRook = new Rook(Color.BLACK);
		game.board().placePiece(new Square("a7"), whitePawn);
		game.board().placePiece(new Square("b8"), blackRook);
		
		PromotionMove move = (PromotionMove) game.promote(new Square("a7"), new Square("b8"), PieceType.QUEEN);
		assertSame(blackRook, move.opposingPiece());
		assertEquals(1, game.capturedPieces(Color.BLACK).size());
		assertTrue(game.capturedPieces(Color.BLACK).contains(blackRook));
	}
	
	@Test
	public void testPromotionNotPerformedWhenItResultsInCheck() {
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece whiteKing = new King(Color.WHITE);
		Piece blackRook = new Rook(Color.BLACK);
		game.board().placePiece(new Square("d7"), whitePawn);
		game.board().placePiece(new Square("a7"), blackRook);
		game.board().placePiece(new Square("f7"), whiteKing);
		
		Move move = game.promote(new Square("d7"), new Square("d8"), PieceType.QUEEN);
		assertEquals(MoveType.INVALID, move.moveType());
		assertEquals(0, game.moves().size());
		assertSame(whitePawn, game.board().pieceOn(new Square("d7")));
		assertSame(blackRook, game.board().pieceOn(new Square("a7")));
		assertSame(whiteKing, game.board().pieceOn(new Square("f7")));
	}
	
	@Test
	public void testQueenCanBeUsedAsPromotionPiece() {
		PromotionMove move = (PromotionMove)performSimplePromotion(PieceType.QUEEN);
		assertEquals(MoveType.PROMOTION, move.moveType());
		assertEquals(PieceType.QUEEN, move.promotionPiece().pieceType());
	}
	
	@Test
	public void testRookCanBeUsedAsPromotionPiece() {
		PromotionMove move = (PromotionMove)performSimplePromotion(PieceType.ROOK);
		assertEquals(MoveType.PROMOTION, move.moveType());
		assertEquals(PieceType.ROOK, move.promotionPiece().pieceType());
	}
	
	@Test
	public void testBishopCanBeUsedAsPromotionPiece() {
		PromotionMove move = (PromotionMove)performSimplePromotion(PieceType.BISHOP);
		assertEquals(MoveType.PROMOTION, move.moveType());
		assertEquals(PieceType.BISHOP, move.promotionPiece().pieceType());
	}
	
	@Test
	public void testKnightCanBeUsedAsPromotionPiece() {
		PromotionMove move = (PromotionMove)performSimplePromotion(PieceType.KNIGHT);
		assertEquals(MoveType.PROMOTION, move.moveType());
		assertEquals(PieceType.KNIGHT, move.promotionPiece().pieceType());
	}
	
	@Test
	public void testKingCannotBeUsedAsPromotionPiece() {
		Move move = performSimplePromotion(PieceType.KING);
		assertEquals(MoveType.INVALID, move.moveType());
	}
	
	@Test
	public void testPawnCannotBeUsedAsPromotionPiece() {
		Move move = performSimplePromotion(PieceType.PAWN);
		assertEquals(MoveType.INVALID, move.moveType());
	}
	
	@Test
	public void testPromotionReturnsCorrectClearedAndUpdatedSquares() {
		Piece whitePawn = new Pawn(Color.WHITE);
		game.board().placePiece(new Square("a7"), whitePawn);
		Move move = game.promote(new Square("a7"), new Square("a8"), PieceType.QUEEN);
		
		assertEquals(1, move.clearedSquares().size());
		assertEquals(new Square("a7"), move.clearedSquares().get(0));
		assertEquals(1, move.updatedSquares().keySet().size());
		Square promoteSquare = move.updatedSquares().keySet().iterator().next();
		assertEquals(new Square("a8"), promoteSquare);
		assertEquals(PieceType.QUEEN, move.updatedSquares().get(promoteSquare).pieceType());
	}
	
	@Test
	public void testMovePromotionCapturedPieceIsNull() {
		Move move = performSimplePromotion();
		assertNull(move.capturedPiece());
	}
	
	@Test
	public void testCapturePromotionCapturedPieceIsCorrect() {
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackRook = new Rook(Color.BLACK);
		game.board().placePiece(new Square("a7"), whitePawn);
		game.board().placePiece(new Square("b8"), blackRook);
		
		Move move = game.promote(new Square("a7"), new Square("b8"), PieceType.QUEEN);
		assertEquals(blackRook, move.capturedPiece());
	}
			
	private Move performSimplePromotion() {
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackKing = new King(Color.BLACK);
		game.board().placePiece(new Square("a7"), whitePawn);
		game.board().placePiece(new Square("a1"), blackKing);
		
		return game.promote(new Square("a7"), new Square("a8"), PieceType.QUEEN);
	}
	
	public Move performSimplePromotion(Piece whitePawn) {
		Piece blackKing = new King(Color.BLACK);
		game.board().placePiece(new Square("a7"), whitePawn);
		game.board().placePiece(new Square("a1"), blackKing);
		
		return game.promote(new Square("a7"), new Square("a8"), PieceType.QUEEN);
	}
	
	private Move performSimplePromotion(PieceType pieceType) {
		Piece whitePawn = new Pawn(Color.WHITE);
		Piece blackKing = new King(Color.BLACK);
		game.board().placePiece(new Square("a7"), whitePawn);
		game.board().placePiece(new Square("a1"), blackKing);
		
		return game.promote(new Square("a7"), new Square("a8"), pieceType);
	}
	
}
