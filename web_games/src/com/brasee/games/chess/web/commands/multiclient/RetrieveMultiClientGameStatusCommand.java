package com.brasee.games.chess.web.commands.multiclient;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.GamesUser;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.MultiClientGame;

public class RetrieveMultiClientGameStatusCommand extends AbstractMultiClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, MultiClientGame game) {
		GamesUser user = getUserFromRequest(request);
		Map<String, Object> responseMap = createGameStateResponseMap(game, user);
		return new JsonView(responseMap);
	}

}
