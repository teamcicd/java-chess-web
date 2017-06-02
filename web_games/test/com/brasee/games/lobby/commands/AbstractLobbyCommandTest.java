package com.brasee.games.lobby.commands;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.UserManager;

public abstract class AbstractLobbyCommandTest {

	@SuppressWarnings("unchecked")
	public String processRequest(MockHttpServletRequest request, Class expectedCommandClass) {
		return processRequest(request, expectedCommandClass, new UserManager(7000L, 3500L), new ChatManager(100, 10));
	}
	
	@SuppressWarnings("unchecked")
	public String processRequest(MockHttpServletRequest request, Class expectedCommandClass, ChatManager chatManager) {
		return processRequest(request, expectedCommandClass, new UserManager(7000L, 3500L), chatManager);
	}
	
	@SuppressWarnings("unchecked")
	public String processRequest(MockHttpServletRequest request, Class expectedCommandClass, UserManager userManager) {
		return processRequest(request, expectedCommandClass, userManager, new ChatManager(100, 10));
	}
	
	@SuppressWarnings("unchecked")
	public String processRequest(MockHttpServletRequest request, Class expectedCommandClass, UserManager userManager, 
			ChatManager chatManager) {
		String jsonStringResult = null;

		LobbyCommand lobbyCommand = LobbyCommandFactory.createCommand(request);
		assertTrue(lobbyCommand.getClass().equals(expectedCommandClass));

		JsonView view = lobbyCommand.processCommand(request, new UserManager(7000L, 3500L), chatManager);
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		try {
			view.render(null, request, response);
			jsonStringResult = response.getContentAsString();
		}
		catch (Exception e) {
			fail("Should not have thrown an exception");
		}
		
		return jsonStringResult;
	}
	
}
