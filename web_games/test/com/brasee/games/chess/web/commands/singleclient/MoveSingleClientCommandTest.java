package com.brasee.games.chess.web.commands.singleclient;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.brasee.chess.Game;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.chess.web.commands.singleclient.SingleClientChessCommand;
import com.brasee.games.chess.web.commands.singleclient.SingleClientChessCommandFactory;

public class MoveSingleClientCommandTest {

	@Test
	public void testMoveCommandReturnsCorrectViewForValidInput() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "move");
		request.addParameter("start_square", "a2");
		request.addParameter("end_square", "a3");		
		
		String expectedResult = "{\"players_turn\":\"black\",\"move_notation\":\"a2.a3\",\"captured_piece\":null,\"cleared_squares\":[{\"square\":\"a2\"}],\"updated_squares\":[{\"piece\":\"pawn_white\",\"square\":\"a3\"}],\"move_index\":\"1\",\"move_type\":\"normal\"}";
		assertEquals(expectedResult, processRequestWithNewGame(request));
	}
	
	@Test
	public void testMoveCommandReturnsInvalidMoveTypeForInvalidInput() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "move");
		// no start and end square in request
		
		String expectedResult = "{\"move_type\":\"invalid\"}";
		assertEquals(expectedResult, processRequestWithNewGame(request));
	}
	
	private String processRequestWithNewGame(HttpServletRequest request) {
		String jsonStringResult = null;
		
		Game game = new Game();
		game.initializeBoard();
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		JsonView view = command.processCommand(request, game);
		
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
