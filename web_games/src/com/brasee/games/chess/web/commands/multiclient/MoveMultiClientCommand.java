package com.brasee.games.chess.web.commands.multiclient;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.brasee.chess.Square;
import com.brasee.chess.moves.Move;
import com.brasee.games.GamesUser;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.lobby.MultiClientGame;

public class MoveMultiClientCommand extends AbstractMultiClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, MultiClientGame game) {
		GamesUser user = getUserFromRequest(request);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
			String startSquare = request.getParameter("start_square");
			String endSquare = request.getParameter("end_square");
			Move move = game.move(new Square(startSquare), new Square(endSquare), user);
			responseMap = createGameStateResponseMap(game, user);
			addSingleMoveInfoToResponse(move, responseMap);
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
			responseMap.put("move_type", "invalid");
		}
		
		return new JsonView(responseMap);
	}

}
