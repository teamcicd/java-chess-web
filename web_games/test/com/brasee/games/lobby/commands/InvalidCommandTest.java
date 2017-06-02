package com.brasee.games.lobby.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class InvalidCommandTest extends AbstractLobbyCommandTest {

	@Test
	public void testInvalidCommandReturnsFailureResult() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("some_meaningless_parameter", "some_meaningless_value");
		String expectedResult = "{\"result\":\"failure\"}";
		assertEquals(expectedResult, processRequest(request, InvalidCommand.class));
	}
	
}
