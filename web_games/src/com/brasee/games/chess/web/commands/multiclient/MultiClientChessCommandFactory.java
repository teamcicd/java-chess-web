package com.brasee.games.chess.web.commands.multiclient;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class MultiClientChessCommandFactory {

	private static final List<String> VALID_COMMANDS = 
		Arrays.asList(new String[] {"retrieve_game", "retrieve_game_status", "reset_game", "move", "promote"});
	
	public static boolean isValidCommandRequest(HttpServletRequest request) {
		String commandName = request.getParameter("command");
		return VALID_COMMANDS.contains(commandName);
	}
	
	public static MultiClientChessCommand createCommand(HttpServletRequest request) {
		MultiClientChessCommand chessCommand = new InvalidMultiClientCommand();
		
		String commandName = request.getParameter("command");
		
		if ("retrieve_game".equals(commandName)) {
			chessCommand = new RetrieveMultiClientGameCommand();
		}
		else if ("retrieve_game_status".equals(commandName)) {
			chessCommand = new RetrieveMultiClientGameStatusCommand();
		}
		else if ("reset_game".equals(commandName)) {
			chessCommand = new ResetMultiClientGameCommand();
		}
		else if ("move".equals(commandName)) {
			chessCommand = new MoveMultiClientCommand();
		}
		else if ("promote".equals(commandName)) {
			chessCommand = new PromoteMultiClientCommand();
		}

		return chessCommand;
	}
	
}
