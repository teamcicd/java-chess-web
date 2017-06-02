package com.brasee.games.chess.web.commands.multiclient;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.MultiClientGame;

public class InvalidMultiClientCommand implements MultiClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, MultiClientGame game) {
		return null;
	}

}
