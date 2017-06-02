package com.brasee.games.lobby.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.brasee.games.GamesUser;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.LobbyUiController;
import com.brasee.games.lobby.UserManager;

public class RefreshUserCommand implements LobbyCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, UserManager userManager, ChatManager chatManager) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
			HttpSession session = request.getSession();
			String sessionId = session.getId();
			GamesUser user = (GamesUser) session.getAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE);
			String userName = user.getName();
			if (sessionId != null && userName != null) {
				userManager.refreshUser(sessionId, userName);
			}
			else {
				throw new IllegalArgumentException("sessionId or userName is null");
			}
			responseMap.put("result", "success");
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
			responseMap.put("result", "failure");	
		}
		
		return new JsonView(responseMap);
	}

}
