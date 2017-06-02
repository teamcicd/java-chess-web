package com.brasee.games.chess.web.commands.singleclient;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.brasee.chess.Game;
import com.brasee.games.chess.web.JsonView;

public class RetrieveSingleClientGameCommand extends AbstractSingleClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, Game game) {
		Map<String, Object> responseMap = createGameStateResponseMap(game);
		addFullGameInfoToResponse(game, responseMap);
		return new JsonView(responseMap);
	}

}
