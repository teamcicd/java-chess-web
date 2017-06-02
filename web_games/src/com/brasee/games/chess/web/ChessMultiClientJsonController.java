package com.brasee.games.chess.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.brasee.games.chess.web.commands.multiclient.MultiClientChessCommand;
import com.brasee.games.chess.web.commands.multiclient.MultiClientChessCommandFactory;
import com.brasee.games.lobby.MultiClientGame;
import com.brasee.games.lobby.MultiClientGameManager;
import com.brasee.games.lobby.commands.LobbyCommand;
import com.brasee.games.lobby.commands.LobbyCommandFactory;

public class ChessMultiClientJsonController extends AbstractController {

	private MultiClientGameManager gameManager;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		MultiClientGame game = gameManager.retrieveGame(request.getParameter("gameId"));
		
		JsonView jsonView = null;

		if (game != null) {
			if (MultiClientChessCommandFactory.isValidCommandRequest(request)) {
				MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
				jsonView = command.processCommand(request, game);
			}
			else if (LobbyCommandFactory.isValidCommandRequest(request)) {
				LobbyCommand command = LobbyCommandFactory.createCommand(request);
				jsonView = command.processCommand(request, game.getUserManager(), game.getChatManager());
			}
		}
		
		return new ModelAndView(jsonView);
	}

	public void setGameManager(MultiClientGameManager gameManager) {
		this.gameManager = gameManager;
	}
	
}
