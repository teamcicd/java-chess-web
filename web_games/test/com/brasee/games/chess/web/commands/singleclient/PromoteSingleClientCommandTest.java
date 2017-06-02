package com.brasee.games.chess.web.commands.singleclient;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.brasee.chess.Game;
import com.brasee.chess.Square;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.chess.web.commands.singleclient.SingleClientChessCommand;
import com.brasee.games.chess.web.commands.singleclient.SingleClientChessCommandFactory;

public class PromoteSingleClientCommandTest {

	@Test
	public void testMoveCommandReturnsCorrectViewForValidInput() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "promote");
		request.addParameter("start_square", "c7");
		request.addParameter("end_square", "b8");
		request.addParameter("piece_type", "queen");
		
		String expectedResult = "{\"players_turn\":\"black\",\"move_notation\":\"c7.b8\",\"captured_piece\":\"knight_black\",\"cleared_squares\":[{\"square\":\"c7\"}],\"updated_squares\":[{\"piece\":\"queen_white\",\"square\":\"b8\"}],\"move_index\":\"9\",\"move_type\":\"promotion\"}";
		assertEquals(expectedResult, processRequestWithPromotionSetupGame(request));
	}
	
	@Test
	public void testPromoteCommandReturnsInvalidMoveTypeForInvalidInput() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "promote");
		// no start square, end square or piece type in request
		
		String expectedResult = "{\"move_type\":\"invalid\"}";
		assertEquals(expectedResult, processRequestWithPromotionSetupGame(request));
	}
	
	private String processRequestWithPromotionSetupGame(HttpServletRequest request) {
		String jsonStringResult = null;
		
		Game game = new Game();
		game.initializeBoard();
		game.move(new Square("b2"), new Square("b4"));
		game.move(new Square("a7"), new Square("a5"));
		game.move(new Square("b4"), new Square("a5"));
		game.move(new Square("b7"), new Square("b6"));
		game.move(new Square("a5"), new Square("b6"));
		game.move(new Square("h7"), new Square("h6"));
		game.move(new Square("b6"), new Square("c7"));
		game.move(new Square("h6"), new Square("h5"));
		
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
