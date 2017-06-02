package com.brasee.games.lobby.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.UserManager;

public class RetrieveMessageIndexCommand implements LobbyCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, UserManager userManager, ChatManager chatManager) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
			responseMap.put("message_index", chatManager.getMessageIndex());
			responseMap.put("result", "success");
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
			responseMap.put("result", "failure");
		}
		
		return new JsonView(responseMap);
	}

}
