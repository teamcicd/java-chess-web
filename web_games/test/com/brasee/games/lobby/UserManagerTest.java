package com.brasee.games.lobby;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserManagerTest {

	private UserManager manager;
	
	@Before
	public void setUp() {
		manager = new UserManager(7000L, 3500L);
	}
	
	@Test
	public void testNewUserManagerHasNoUserNames() {
		assertNotNull(manager.getCurrentUserNames());
		assertTrue(manager.getCurrentUserNames().isEmpty());
		assertFalse(manager.isConnected("some random sessionId"));
	}
	
	@Test
	public void testSingleUserRefreshAddsOneUserName() {
		String userName = "User Name";
		manager.refreshUser("sessionId", userName);
		List<String> users = manager.getCurrentUserNames();
		assertEquals(new Integer(1), new Integer(users.size()));
		assertEquals(userName, users.get(0));
		assertTrue(manager.isConnected("sessionId"));
	}
	
	@Test
	public void testMultipleUserRefreshForSameUserKeepsOneUserName() {
		String userName = "User Name";
		manager.refreshUser("sessionId", userName);
		manager.refreshUser("sessionId", userName);
		List<String> users = manager.getCurrentUserNames();
		assertEquals(new Integer(1), new Integer(users.size()));
		assertEquals(userName, users.get(0));
	}
	
	@Test
	public void testMultipleUserRefreshReturnsUserNamesInCorrectOrder() {
		String alpha = "Alpha";
		String beta = "Beta";
		manager.refreshUser("sessionId1", alpha);
		manager.refreshUser("sessionId2", beta);
		List<String> users = manager.getCurrentUserNames();
		assertEquals(new Integer(2), new Integer(users.size()));
		assertEquals(alpha, users.get(0));
		assertEquals(beta, users.get(1));
	}
	
	@Test
	public void testMultipleUserRefreshInDifferentOrderReturnsUserNamesInCorrectOrder() {
		String alpha = "Alpha";
		String beta = "Beta";
		manager.refreshUser("sessionId2", beta);
		manager.refreshUser("sessionId1", alpha);
		List<String> users = manager.getCurrentUserNames();
		assertEquals(new Integer(2), new Integer(users.size()));
		assertEquals(alpha, users.get(0));
		assertEquals(beta, users.get(1));
	}
	
	@Test
	public void testExpiryRemovesAUserFromCurrentUsersList() throws Exception {
		// Note: negative expiration time limits are stupid, and used for unit testing purposed only
		manager = new UserManager(-1, 50);
		manager.refreshUser("sessionId", "User Name");
		Thread.sleep(100);
		assertTrue(manager.getCurrentUserNames().isEmpty());
	}
}
