package com.brasee.games.lobby;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.brasee.chess.Game;
import com.brasee.chess.Square;
import com.brasee.chess.moves.InvalidMove;
import com.brasee.chess.moves.Move;
import com.brasee.chess.moves.Move.MoveType;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Piece.Color;
import com.brasee.chess.pieces.Piece.PieceType;
import com.brasee.games.GamesUser;

public class MultiClientGame {

	private static final long PLAYER_EXPIRY_TIMER_REFRESH = 3500L;
	private Game game;
	private ConcurrentMap<Color, GamesUser> players;
	private BufferedImage previewImage;
	private GamePreviewImageGenerator imageGenerator;
	private UserManager userManager;
	private ChatManager chatManager;
	private Timer playerExpiryTimer;
	
	private Object previewImageLock = new Object();
	
	public MultiClientGame(GamePreviewImageGenerator imageGenerator, final UserManager userManager, ChatManager chatManager) {
		this.imageGenerator	= imageGenerator;
		
		this.players = new ConcurrentHashMap<Color, GamesUser>();
		this.game = new Game();
		this.game.initializeBoard();

		this.previewImage = this.imageGenerator.createPngPreviewImage(this.game);
		this.userManager = userManager;
		this.chatManager = chatManager;
		
		this.playerExpiryTimer = new Timer(true);
		this.playerExpiryTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (players.get(Color.WHITE) != null && !userManager.isConnected(players.get(Color.WHITE).getSessionId())) {
					players.remove(Color.WHITE);
				}
				if (players.get(Color.BLACK) != null && !userManager.isConnected(players.get(Color.BLACK).getSessionId())) {
					players.remove(Color.BLACK);
				}
			}
		}, 0, PLAYER_EXPIRY_TIMER_REFRESH);
	}
	
	public Move move(Square startSquare, Square endSquare, GamesUser user) {
		Move move = InvalidMove.execute();
		if (isUsersTurn(user)) {
			move = game.move(startSquare, endSquare);
			if (!move.moveType().equals(MoveType.INVALID)) {
				synchronized (previewImageLock) {
					this.previewImage = this.imageGenerator.createPngPreviewImage(game);	
				}
			}
		}
		return move;
	}
	
	public List<Move> moves() {
		return game.moves();
	}
	
	public Color playersTurn() {
		return game.playersTurn();
	}
	
	public Move promote(Square startSquare, Square endSquare, PieceType pieceType, GamesUser user) {
		Move move = InvalidMove.execute();
		if (isUsersTurn(user)) {
			move = game.promote(startSquare, endSquare, pieceType);
			if (!move.moveType().equals(MoveType.INVALID)) {
				synchronized (previewImageLock) {
					this.previewImage = this.imageGenerator.createPngPreviewImage(game);
				}
			}
		}
		return move;
	}
	
	public boolean reset(GamesUser user) {
		boolean success = false;
		if (user != null && (user.equals(getPlayer(Color.WHITE)) || user.equals(getPlayer(Color.BLACK)))) {
			game = new Game();
			game.initializeBoard();
			synchronized (previewImageLock) {
				this.previewImage = this.imageGenerator.createPngPreviewImage(game);
			}
			success = true;
		}
		return success;
	}
	
	public Map<Square, Piece> pieces() {
		return game.pieces();
	}
	
	public List<Piece> capturedPieces(Color color) {
		return game.capturedPieces(color);
	}
	
	public BufferedImage getPreviewImage() {
		synchronized (previewImageLock) {
			return previewImage;
		}
	}
	
	public GamesUser getPlayer(Color color) {
		return players.get(color);
	}

	public boolean addPlayerIfColorIsAvailable(Color color, GamesUser user) {
		boolean addSucceeded = false;
		Color otherColor = color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
		if (!user.equals(players.get(otherColor))) {
			GamesUser existingUser = players.putIfAbsent(color, user);
			if (existingUser == null) {
				addSucceeded = true;
			}
		}
		return addSucceeded;
	}
		
	private boolean isUsersTurn(GamesUser user) {
		return user != null && user.equals(players.get(playersTurn()));
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}
	
}
