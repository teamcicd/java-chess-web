package com.brasee.games.chess.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.brasee.games.GamesUser;
import com.brasee.games.lobby.LobbyUiController;

public class ChessSingleClientUiController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GamesUser user = (GamesUser) request.getSession().getAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE);
		ModelAndView modelAndView = new ModelAndView("chessSingleClient");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

}
