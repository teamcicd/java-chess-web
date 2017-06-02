package com.brasee.chess.pieces;

import org.junit.Test;

import com.brasee.chess.pieces.Piece.Color;

import static org.junit.Assert.*;

public class AbstractPieceTest {

	@Test
	public void testWhiteIsAValidColor() {
		try {
			new Pawn(Color.WHITE);
		}
		catch (InvalidPieceColorException e) {
			fail("No exception should be thrown, Color.WHITE is a valid color");
		}
	}
	
	@Test
	public void testBlackIsAValidColor() {
		try {
			new Pawn(Color.BLACK);
		}
		catch (InvalidPieceColorException e) {
			fail("No exception should be thrown, Color.BLACK is a valid color");
		}
	}
	
	@Test
	public void testExceptionThrownForInvalidColor() {
		try {
			new Pawn(null);
		}
		catch (InvalidPieceColorException e) { 
			return;
		}
		fail("InvalidPieceColorException should have been thrown");
	}
	
	@Test
	public void testSamePieceIsEqual() {
		Piece blackPawn = new Pawn(Color.BLACK);
		assertTrue(blackPawn.equals(blackPawn));
	}

	@Test
	public void testSamePieceTypeAndColorAreEqual() {
		Piece blackPawn1 = new Pawn(Color.BLACK);
		Piece blackPawn2 = new Pawn(Color.BLACK);
		assertTrue(blackPawn1.equals(blackPawn2));
	}

	@Test
	public void testDifferentPieceTypeIsNotEqual() {
		Piece blackPawn = new Pawn(Color.BLACK);
		Piece blackRook = new Rook(Color.BLACK);
		assertFalse(blackPawn.equals(blackRook));
	}
	
	@Test
	public void testDifferentColorIsNotEqual() {
		Piece blackPawn = new Pawn(Color.BLACK);
		Piece whitePawn = new Pawn(Color.WHITE);
		assertFalse(blackPawn.equals(whitePawn));
	}
	
	@Test
	public void testNullIsNotEqual() {
		Piece blackPawn = new Pawn(Color.BLACK);
		assertFalse(blackPawn.equals(null));
	}
	
	@Test
	public void testSomeOtherObjectIsNotEqual() {
		Piece blackPawn = new Pawn(Color.BLACK);
		assertFalse(blackPawn.equals(new Object()));
	}

}
