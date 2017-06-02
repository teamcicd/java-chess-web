package com.brasee.chess;

import org.junit.Before;
import org.junit.Test;

import com.brasee.chess.moves.Move.MoveType;

import static org.junit.Assert.*; 

public class GameTurnTest {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testWhitePlayerCanMoveFirst() {
		game.initializeBoard();
		// try to move white pawn
		assertEquals(MoveType.NORMAL, game.move(new Square("a2"), new Square("a3")).moveType());
	}
	
	@Test
	public void testBlackPlayerCannotMoveFirst() {
		game.initializeBoard();
		// try to move black pawn
		assertEquals(MoveType.INVALID, game.move(new Square("a7"), new Square("a6")).moveType());
	}
	
	@Test
	public void testPlayersCanMakeTwoAlternatingMoves() {
		game.initializeBoard();
		// move white pawn
		game.move(new Square("a2"), new Square("a3"));
		// try to move black pawn
		assertEquals(MoveType.NORMAL, game.move(new Square("a7"), new Square("a6")).moveType());
	}
	
	@Test
	public void testPlayersCanMakeThreeAlternatingMoves() {
		game.initializeBoard();
		// move white pawn
		game.move(new Square("a2"), new Square("a3"));
		// move black pawn
		game.move(new Square("a7"), new Square("a6"));
		// try to move another white pawn
		assertEquals(MoveType.NORMAL, game.move(new Square("h2"), new Square("h3")).moveType());		
	}
	
	@Test
	public void testPlayersCannotMakeSequentialMoves() {
		game.initializeBoard();
		// move white pawn
		game.move(new Square("a2"), new Square("a3"));
		// try to move white pawn again
		assertEquals(MoveType.INVALID, game.move(new Square("a3"), new Square("a4")).moveType());
	}
		
}
