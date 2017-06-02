package com.brasee.games.lobby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.brasee.games.chess.web.JsonView;

public class LobbyJsonControllerTest {

	@Test
	public void testControllerProcessesCommandSuccessfully() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		request.setSession(session);
		request.addParameter("command", "retrieve_message_index");
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		LobbyJsonController controller = new LobbyJsonController();
		controller.setChatManager(new ChatManager(100, 10));
		ModelAndView modelAndView = null;
		String jsonResponse = null;
		try {
			modelAndView = controller.handleRequestInternal(request, response);
			modelAndView.getView().render(null, request, response);
			jsonResponse = response.getContentAsString();
		}
		catch (Exception e) {
			fail("Should not throw an exception");
		}
		
		// test view has been created and was successful
		assertTrue(modelAndView.getView() instanceof JsonView);
		assertEquals("text/plain", modelAndView.getView().getContentType());
		assertTrue(jsonResponse.contains("\"result\":\"success\""));
	}
	
}
