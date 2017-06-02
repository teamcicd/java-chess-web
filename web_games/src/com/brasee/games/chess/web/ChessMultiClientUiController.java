package com.brasee.games.chess.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.brasee.chess.pieces.Piece.Color;
import com.brasee.games.GamesUser;
import com.brasee.games.lobby.LobbyUiController;
import com.brasee.games.lobby.MultiClientGame;
import com.brasee.games.lobby.MultiClientGameManager;

public class ChessMultiClientUiController extends AbstractController {

	private MultiClientGameManager gameManager;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		String gameId = request.getParameter("gameId");
		MultiClientGame game = gameManager.retrieveGame(gameId);
		GamesUser user = (GamesUser) request.getSession().getAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE);
		if (game != null && user != null) {
			modelAndView = new ModelAndView("chessMultiClient");
			modelAndView.addObject("gameId", gameId);
			String actionParameter = request.getParameter("action");
			String colorParameter = request.getParameter("color");
			if ("join".equals(actionParameter) && ("white".equals(colorParameter) || "black".equals(colorParameter))) {
				Color color = "white".equals(colorParameter) ? Color.WHITE : Color.BLACK;
				boolean addSucceded = game.addPlayerIfColorIsAvailable(color, user);
				if (!addSucceded) {
					Color otherColor = color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
					game.addPlayerIfColorIsAvailable(otherColor, user);
				}
			}
			else {
				if (game.getPlayer(Color.WHITE) == null) {
					game.addPlayerIfColorIsAvailable(Color.WHITE, user);
				}
				else if (game.getPlayer(Color.BLACK) == null) {
					game.addPlayerIfColorIsAvailable(Color.BLACK, user);
				}
			}
		}
		
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	public void setGameManager(MultiClientGameManager gameManager) {
		this.gameManager = gameManager;
	}
	
}
