package com.brasee.games.chess.web.commands.multiclient;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.GamesUser;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.MultiClientGame;

public class ResetMultiClientGameCommand extends AbstractMultiClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, MultiClientGame game) {
		GamesUser user = getUserFromRequest(request);
		game.reset(user);
		Map<String, Object> responseMap = createGameStateResponseMap(game, user);
		addFullGameInfoToResponse(game, responseMap);
		return new JsonView(responseMap);
	}

}
