package com.brasee.games.lobby.commands;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.UserManager;

public class InvalidCommand implements LobbyCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, UserManager userManager, ChatManager chatManager) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("result", "failure");
		return new JsonView(responseMap);
	}

}
