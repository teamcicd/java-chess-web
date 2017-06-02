package com.brasee.games.lobby;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class MultiClientGameManagerTest {

	private static final int DEFAULT_NUMBER_OF_GAMES = 6;
	
	private MultiClientGameManager manager;
	
	@Before
	public void setUp() {
		manager = new MultiClientGameManager(DEFAULT_NUMBER_OF_GAMES, new GamePreviewImageGeneratorFactory());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNumberOfGamesMustBeGreaterThan0() {
		new MultiClientGameManager(0, new GamePreviewImageGeneratorFactory());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testImageGeneratorFactoryMustNotBeNull() {
		new MultiClientGameManager(6, null);
	}
	
	@Test
	public void testVerifyAllGamesInstantiated() {
		for (int i = 1; i <= 6; i++) {
			assertNotNull(manager.getGame(i));
		}
		assertNull(manager.getGame(0));
		assertNull(manager.getGame(-1));
		assertNull(manager.getGame(50));
	}
	
}
