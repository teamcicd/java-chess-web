package com.brasee.games.chess.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class ChessSingleClientUiControllerTest {

	@Test
	public void testControllerReturnsCorrectModelAndView() {
		ChessSingleClientUiController controller = new ChessSingleClientUiController();
		ModelAndView modelAndView = null;
		try {
			modelAndView = controller.handleRequest(new MockHttpServletRequest("GET", null), 
					new MockHttpServletResponse());
		}
		catch (Exception e) {
			fail("Should not throw an exception");
		}
		assertNotNull(modelAndView);
		assertTrue(modelAndView.getModel().containsKey("user"));
		assertEquals("chessSingleClient", modelAndView.getViewName());
	}
}
