package com.brasee.games.lobby.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.brasee.games.GamesUser;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.ChatManager;
import com.brasee.games.lobby.ChatResult;
import com.brasee.games.lobby.ChatStringUtils;
import com.brasee.games.lobby.LobbyUiController;
import com.brasee.games.lobby.UserManager;

public class AddMessageCommand implements LobbyCommand {

	private SimpleDateFormat timestampFormatter = new SimpleDateFormat("h:mm:ss a, M/d/yy");
	
	@Override
	public JsonView processCommand(HttpServletRequest request, UserManager userManager, ChatManager chatManager) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
			String message = ChatStringUtils.escapeAndLimitString(request.getParameter("message"));
			GamesUser user = (GamesUser) request.getSession().getAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE);
			String fullMessage = user.getName() + ": " + message + generateTimestampHtml();
			int messageIndex = Integer.parseInt(request.getParameter("message_index"));
			
			// if the user didn't enter anything, set fullMessage to null so it isn't added to the ChatManager
			if (message == null || message.length() == 0) {
				fullMessage = null;
			}
			
			ChatResult<String> result = chatManager.addMessage(fullMessage, messageIndex);
			responseMap.put("result", "success");
			responseMap.put("message_index", new Integer(result.getNextMessageIndex()));
			responseMap.put("messages", result.getMessages());
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
			responseMap.put("result", "failure");
		}
		
		return new JsonView(responseMap);
	}
	
	private String generateTimestampHtml() {
		return "<span class=\"timestampText\">&nbsp;&nbsp;(" + timestampFormatter.format(new Date()) + ")</span>";
	}

}
