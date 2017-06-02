package com.brasee.games.lobby.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.brasee.games.GamesUser;
import com.brasee.games.lobby.LobbyUiController;

public class RetrieveUsersCommandTest extends AbstractLobbyCommandTest {

	private GamesUser user;
	private MockHttpServletRequest request;
	private MockHttpSession session;
	
	@Before
	public void setUp() {
		user = new GamesUser("User", "111111");
		
		request = new MockHttpServletRequest();
		session = new MockHttpSession();
		session.setAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE, user);
		request.setSession(session);
	}
	
	@Test
	public void testRetrieveUsersCommandReturnsSuccessResultForValidInput() {
		request.addParameter("command", "retrieve_users");
		
		String expectedResult = "{\"result\":\"success\",\"users\":[]}";
		assertEquals(expectedResult, processRequest(request, RetrieveUsersCommand.class));
	}
	
}
