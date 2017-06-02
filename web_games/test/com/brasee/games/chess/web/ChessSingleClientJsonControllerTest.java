package com.brasee.games.chess.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.brasee.chess.Game;
import com.brasee.chess.pieces.Piece.Color;

public class ChessSingleClientJsonControllerTest {

	@Test
	public void testControllerProcessesCommandSuccessfully() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		request.setSession(session);
		request.addParameter("command", "retrieve_game");
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		ChessSingleClientJsonController controller = new ChessSingleClientJsonController();
		ModelAndView modelAndView = null;
		try {
			modelAndView = controller.handleRequestInternal(request, response);
		}
		catch (Exception e) {
			fail("Should not throw an exception");
		}
		
		// test view has been created
		assertTrue(modelAndView.getView() instanceof JsonView);
		assertEquals("text/plain", modelAndView.getView().getContentType());
		
		// test game has been properly initialized and saved to session 
		assertNotNull(session.getAttribute(ChessSingleClientJsonController.CHESS_SINGLE_CLIENT_GAME_SESSION_VARIABLE));
		Game game = (Game) session.getAttribute(ChessSingleClientJsonController.CHESS_SINGLE_CLIENT_GAME_SESSION_VARIABLE);
		assertEquals(Color.WHITE, game.playersTurn());
	}
}
