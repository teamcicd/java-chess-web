package com.brasee.games.chess.web.commands.singleclient;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.brasee.chess.Game;
import com.brasee.games.chess.web.ChessSingleClientJsonController;
import com.brasee.games.chess.web.JsonView;

public class ResetSingleClientGameCommand extends AbstractSingleClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, Game game) {
		Game newGame = resetGame(request);
		Map<String, Object> responseMap = createGameStateResponseMap(newGame);
		addFullGameInfoToResponse(newGame, responseMap);
		return new JsonView(responseMap);
	}

	private Game resetGame(HttpServletRequest request) {
		request.getSession().removeAttribute(ChessSingleClientJsonController.CHESS_SINGLE_CLIENT_GAME_SESSION_VARIABLE);
		
		Game newGame = new Game();
		newGame.initializeBoard();
		request.getSession().setAttribute(ChessSingleClientJsonController.CHESS_SINGLE_CLIENT_GAME_SESSION_VARIABLE, newGame);
		return newGame;
	}

}
