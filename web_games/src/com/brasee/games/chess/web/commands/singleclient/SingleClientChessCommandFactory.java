package com.brasee.games.chess.web.commands.singleclient;

import javax.servlet.http.HttpServletRequest;


public class SingleClientChessCommandFactory {

	public static SingleClientChessCommand createCommand(HttpServletRequest request) {
		SingleClientChessCommand chessCommand = new InvalidSingleClientCommand();
		
		String commandName = request.getParameter("command");
		
		if ("retrieve_game".equals(commandName)) {
			chessCommand = new RetrieveSingleClientGameCommand();
		}
		else if ("reset_game".equals(commandName)) {
			chessCommand = new ResetSingleClientGameCommand();
		}
		else if ("move".equals(commandName)) {
			chessCommand = new MoveSingleClientCommand();
		}
		else if ("promote".equals(commandName)) {
			chessCommand = new PromoteSingleClientCommand();
		}

		return chessCommand;
	}
}
