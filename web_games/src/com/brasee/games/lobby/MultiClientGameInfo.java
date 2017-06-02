package com.brasee.games.lobby;

import com.brasee.chess.pieces.Piece.Color;

public class MultiClientGameInfo {

	private String description = null;
	private String blackPlayerName = null;
	private String whitePlayerName = null;
	
	private MultiClientGameInfo(MultiClientGame game) {
		if (game == null) {
			throw new IllegalArgumentException("MultipleClientGame parameter must not be null");
		}
		
		this.whitePlayerName = game.getPlayer(Color.WHITE) != null ? game.getPlayer(Color.WHITE).getName() : null;
		this.blackPlayerName = game.getPlayer(Color.BLACK) != null ? game.getPlayer(Color.BLACK).getName() : null;
		this.description = generateGameDescription(game);
	}
	
	public static MultiClientGameInfo create(MultiClientGame game) {
		return new MultiClientGameInfo(game);
	}
	
	public String getDescription() {
		return description;
	}

	public String getBlackPlayerName() {
		return blackPlayerName;
	}

	public String getWhitePlayerName() {
		return whitePlayerName;
	}
	
	private static String generateGameDescription(MultiClientGame game) {
		String gameDescription = "";

		if (game.getPlayer(Color.WHITE) == null && game.getPlayer(Color.BLACK) == null) {
			gameDescription = "Empty game";
		}
		else if (game.getPlayer(Color.WHITE) != null && game.getPlayer(Color.BLACK) != null) {
			gameDescription = game.getPlayer(Color.WHITE).getName() + " vs. " + game.getPlayer(Color.BLACK).getName();
		}
		else {
			String currentPlayerName = game.getPlayer(Color.WHITE) != null ? game.getPlayer(Color.WHITE).getName() : 
				game.getPlayer(Color.BLACK).getName();
			gameDescription = currentPlayerName + " waiting for opponent";
		}

		return gameDescription;
	}
}
