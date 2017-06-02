package com.brasee.games.chess.web;

import java.util.HashMap;
import java.util.Map;

import com.brasee.chess.pieces.Piece.PieceType;

public class PieceTypeStringConverter {

	private static Map<String, PieceType> stringToPieceType = new HashMap<String, PieceType>();
	private static Map<PieceType, String> pieceTypeToString = new HashMap<PieceType, String>();
	
	static {
		stringToPieceType.put("queen", PieceType.QUEEN);
		stringToPieceType.put("knight", PieceType.KNIGHT);
		stringToPieceType.put("rook", PieceType.ROOK);
		stringToPieceType.put("bishop", PieceType.BISHOP);
		stringToPieceType.put("king", PieceType.KING);
		stringToPieceType.put("pawn", PieceType.PAWN);
		
		for (String string : stringToPieceType.keySet()) {
			pieceTypeToString.put(stringToPieceType.get(string), string);
		}
	}
	
	public static PieceType stringToPieceType(String string) {
		return stringToPieceType.get(string);
	}
	
	public static String pieceTypeToString(PieceType pieceType) {
		return pieceTypeToString.get(pieceType);
	}
	
}
