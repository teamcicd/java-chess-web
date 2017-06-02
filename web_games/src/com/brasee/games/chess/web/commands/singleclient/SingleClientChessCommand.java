package com.brasee.games.chess.web.commands.singleclient;

import javax.servlet.http.HttpServletRequest;

import com.brasee.chess.Game;
import com.brasee.games.chess.web.JsonView;

public interface SingleClientChessCommand {

	public JsonView processCommand(HttpServletRequest request, Game game);
	
}
