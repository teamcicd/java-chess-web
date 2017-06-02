package com.brasee.games.chess.web.commands.singleclient;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.brasee.chess.Game;
import com.brasee.chess.Square;
import com.brasee.chess.moves.Move;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.chess.web.PieceTypeStringConverter;

public class PromoteSingleClientCommand extends AbstractSingleClientChessCommand {

	@Override
	public JsonView processCommand(HttpServletRequest request, Game game) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
			String startSquare = request.getParameter("start_square");
			String endSquare = request.getParameter("end_square");
			String pieceType = request.getParameter("piece_type");
			Move move = game.promote(new Square(startSquare), new Square(endSquare),
					PieceTypeStringConverter.stringToPieceType(pieceType));
			responseMap = createGameStateResponseMap(game);
			addSingleMoveInfoToResponse(move, responseMap);
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
			responseMap.put("move_type", "invalid");
		}
		
		return new JsonView(responseMap);
	}

}
