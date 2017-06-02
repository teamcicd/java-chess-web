package com.brasee.games.chess.web.commands.multiclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.brasee.chess.Square;
import com.brasee.chess.moves.Move;
import com.brasee.chess.moves.Move.MoveType;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Piece.Color;
import com.brasee.games.GamesUser;
import com.brasee.games.chess.web.JsonSquare;
import com.brasee.games.chess.web.JsonSquarePiece;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.chess.web.PieceTypeStringConverter;
import com.brasee.games.lobby.LobbyUiController;
import com.brasee.games.lobby.MultiClientGame;
import com.brasee.games.lobby.MultiClientGameInfo;

public abstract class AbstractMultiClientChessCommand implements MultiClientChessCommand {

	public abstract JsonView processCommand(HttpServletRequest request,	MultiClientGame game);

	protected Map<String, Object> createGameStateResponseMap(MultiClientGame game, GamesUser user) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("players_turn", playersTurnToString(game.playersTurn()));
		responseMap.put("move_index", Integer.toString(game.moves().size()));
		responseMap.put("player_color", currentPlayerColor(game, user));
		responseMap.put("white_player_name", getPlayerName(Color.WHITE, game));
		responseMap.put("black_player_name", getPlayerName(Color.BLACK, game));
		responseMap.put("description", MultiClientGameInfo.create(game).getDescription());
		return responseMap;
	}
	
	protected void addFullGameInfoToResponse(MultiClientGame game, Map<String, Object> responseMap) {
		responseMap.put("pieces", generateJsonSquarePieceList(game.pieces()));
		responseMap.put("captured_pieces_white", capturedPiecesToStrings(game, Color.WHITE));
		responseMap.put("captured_pieces_black", capturedPiecesToStrings(game, Color.BLACK));
		responseMap.put("move_notations", movesToStrings(game));
	}
	
	protected void addSingleMoveInfoToResponse(Move move, Map<String, Object> responseMap) {
		responseMap.put("move_type", moveTypeToString(move.moveType()));
		responseMap.put("updated_squares", generateJsonSquarePieceList(move.updatedSquares()));
		responseMap.put("cleared_squares", generateJsonSquareList(move.clearedSquares()));
		responseMap.put("captured_piece", capturedPieceToString(move.capturedPiece()));
		responseMap.put("move_notation", move.notation());
	}
	
	protected GamesUser getUserFromRequest(HttpServletRequest request) {
		return (GamesUser) request.getSession().getAttribute(LobbyUiController.GAMES_USER_SESSION_VARIABLE);
	}
	
	private String currentPlayerColor(MultiClientGame game, GamesUser user) {
		if (user == null) {
			return "none";
		}
		if (user.equals(game.getPlayer(Color.WHITE))) {
			return "white";
		}
		else if (user.equals(game.getPlayer(Color.BLACK))) {
			return "black";
		}
		else {
			return "none";
		}
	}

	private List<JsonSquarePiece> generateJsonSquarePieceList(Map<Square, Piece> pieces) {
		List<JsonSquarePiece> jsonPieces = new ArrayList<JsonSquarePiece>();
		for (Square square : pieces.keySet()) {
			jsonPieces.add(new JsonSquarePiece(square, pieces.get(square)));
		}
		return jsonPieces;
	}

	private List<String> capturedPiecesToStrings(MultiClientGame game, Color color) {
		List<String> capturedPieces = new ArrayList<String>();
		for (Piece piece : game.capturedPieces(color)) {
			capturedPieces.add(capturedPieceToString(piece));
		}
		return capturedPieces;
	}
	
	private String capturedPieceToString(Piece piece) {
		if (piece != null) {
			return PieceTypeStringConverter.pieceTypeToString(piece.pieceType()) + "_" + playersTurnToString(piece.color());
		}
		else {
			return null;
		}
	}
	
	private List<JsonSquare> generateJsonSquareList(List<Square> squares) {
		List<JsonSquare> jsonSquares = new ArrayList<JsonSquare>();
		for (Square square : squares) {
			jsonSquares.add(new JsonSquare(square));
		}
		return jsonSquares;
	}
	
	private List<String> movesToStrings(MultiClientGame game) {
		List<String> moves = new ArrayList<String>();
		for (Move move : game.moves()) {
			moves.add(move.notation());
		}
		return moves;
	}
	
	private String moveTypeToString(MoveType moveType) {
		return moveType.toString().toLowerCase();
	}
	
	private String playersTurnToString(Color color) {
		if (color.equals(Color.WHITE)) {
			return "white";
		}
		else {
			return "black";
		}
	}
	
	private String getPlayerName(Color color, MultiClientGame game) {
		String playerName = null;
		GamesUser user = game.getPlayer(color);
		if (user != null) {
			playerName = user.getName();
		}
		return playerName;
	}

}
