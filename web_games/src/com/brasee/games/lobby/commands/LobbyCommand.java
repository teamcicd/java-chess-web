package com.brasee.games.lobby.commands;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.UserManager;

public interface LobbyCommand {

	public JsonView processCommand(HttpServletRequest request, UserManager userManager, ChatManager chatManager);
	
}
