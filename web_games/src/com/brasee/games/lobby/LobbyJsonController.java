package com.brasee.games.lobby;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.commands.LobbyCommand;
import com.brasee.games.lobby.commands.LobbyCommandFactory;

public class LobbyJsonController extends AbstractController {

	private ChatManager chatManager;
	private UserManager userManager;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LobbyCommand command = LobbyCommandFactory.createCommand(request);
		JsonView jsonView = command.processCommand(request, userManager, chatManager);
		return new ModelAndView(jsonView);
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
}
