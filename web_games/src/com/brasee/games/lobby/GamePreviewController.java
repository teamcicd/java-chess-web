package com.brasee.games.lobby;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class GamePreviewController extends AbstractController {

	private MultiClientGameManager gameManager;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		MultiClientGame game = gameManager.retrieveGame(request.getParameter("gameId"));
		if (game != null) {
			modelAndView = new ModelAndView(new GamePreviewView(game));
		}

		return modelAndView;
	}

	public void setGameManager(MultiClientGameManager gameManager) {
		this.gameManager = gameManager;
	}

}
