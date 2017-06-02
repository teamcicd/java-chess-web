package com.brasee.games.lobby.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.UserManager;

public class RetrieveMessagesCommandTest {

	private ChatManager chatManager;
	private UserManager userManager; 
	
	@Before
	public void setUp() {
		this.chatManager = new ChatManager(100, 10);
		this.userManager = new UserManager(7000L, 3500L);
	}
	
	@Test
	public void testRetrieveMessagesCommandReturnsSuccessResultForValidInput() {
		chatManager.addMessage("User: test message", 0);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "retrieve_messages");
		request.addParameter("message_index", "0");
		
		String expectedResult = "{\"result\":\"success\",\"message_index\":1,\"messages\":[\"User: test message\"]}";
		assertEquals(expectedResult, processRetrieveMessagesRequest(request));
	}
	
	@Test
	public void testRetrieveMessagesCommandReturnsFailureResultForInvalidInput() {
		chatManager.addMessage("User: test message", 0);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "retrieve_messages");
		
		String expectedResult = "{\"result\":\"failure\"}";
		assertEquals(expectedResult, processRetrieveMessagesRequest(request));
	}
	
	public String processRetrieveMessagesRequest(MockHttpServletRequest request) {
		String jsonStringResult = null;

		LobbyCommand lobbyCommand = LobbyCommandFactory.createCommand(request);
		assertTrue(lobbyCommand instanceof RetrieveMessagesCommand);

		JsonView view = lobbyCommand.processCommand(request, userManager, chatManager);
		
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
