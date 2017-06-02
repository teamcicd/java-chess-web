package com.brasee.games.lobby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.brasee.games.GamesUser;

public class LobbyUiControllerTest {
	
	@Test
	public void testControllerReturnsCorrectModelAndViewForNewUser() {
		LobbyUiController controller = new LobbyUiController();
		ModelAndView modelAndView = null;
		try {
			modelAndView = controller.handleRequest(new MockHttpServletRequest("GET", null), 
					new MockHttpServletResponse());
		}
		catch (Exception e) {
			fail("Should not throw an exception");
		}
		assertNotNull(modelAndView);
		assertTrue(modelAndView.getModel().isEmpty());
		assertEquals("lobbyLogin", modelAndView.getViewName());
	}
	
	@Test
	public void testControllerReturnsCorrectModelAndViewForExistingUser() {
		LobbyUiController controller = new LobbyUiController();
		controller.setGameManager(new MultiClientGameManager(3, new GamePreviewImageGeneratorFactory()));
		MockHttpSession session = new MockHttpSession();
		GamesUser user = new GamesUser("User", "111111");
		session.setAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE, user);
		MockHttpServletRequest request = new MockHttpServletRequest("GET", null);
		request.setSession(session);
		
		ModelAndView modelAndView = null;
		try {
			modelAndView = controller.handleRequest(request, new MockHttpServletResponse());
		}
		catch (Exception e) {
			fail("Should not throw an exception");
		}
		assertNotNull(modelAndView);
		assertEquals(user, modelAndView.getModel().get("user"));
		assertEquals("lobby", modelAndView.getViewName());
	}
	
}
