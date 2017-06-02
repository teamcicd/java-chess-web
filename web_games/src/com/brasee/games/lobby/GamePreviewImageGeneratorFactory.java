package com.brasee.games.lobby;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;

import com.brasee.chess.pieces.Bishop;
import com.brasee.chess.pieces.King;
import com.brasee.chess.pieces.Knight;
import com.brasee.chess.pieces.Pawn;
import com.brasee.chess.pieces.Piece;
import com.brasee.chess.pieces.Queen;
import com.brasee.chess.pieces.Rook;
import com.brasee.chess.pieces.Piece.Color;

public class GamePreviewImageGeneratorFactory {

	private GamePreviewImageGenerator instance = null;
	private Resource boardResource;
	private Resource whitePawnResource;
	private Resource whiteBishopResource;
	private Resource whiteRookResource;
	private Resource whiteKnightResource;
	private Resource whiteKingResource;
	private Resource whiteQueenResource;
	private Resource blackPawnResource;
	private Resource blackBishopResource;
	private Resource blackRookResource;
	private Resource blackKnightResource;
	private Resource blackKingResource;
	private Resource blackQueenResource;
	
	public synchronized GamePreviewImageGenerator getInstance() {
		if (instance == null) {
			instance = createGamePreviewImageGenerator();
		}
		return instance;
	}

	private GamePreviewImageGenerator createGamePreviewImageGenerator() {
		BufferedImage boardImage = createBufferedImage(boardResource);
		Map<Piece, BufferedImage> pieceImages = loadPieceImages();
		return new GamePreviewImageGenerator(boardImage, pieceImages);
	}

	private Map<Piece, BufferedImage> loadPieceImages() {
		Map<Piece, BufferedImage> pieceImages = new HashMap<Piece, BufferedImage>();

		pieceImages.put(new Pawn(Color.WHITE), createBufferedImage(whitePawnResource));
		pieceImages.put(new Bishop(Color.WHITE), createBufferedImage(whiteBishopResource));
		pieceImages.put(new Rook(Color.WHITE), createBufferedImage(whiteRookResource));
		pieceImages.put(new Knight(Color.WHITE), createBufferedImage(whiteKnightResource));
		pieceImages.put(new King(Color.WHITE), createBufferedImage(whiteKingResource));
		pieceImages.put(new Queen(Color.WHITE), createBufferedImage(whiteQueenResource));
		pieceImages.put(new Pawn(Color.BLACK), createBufferedImage(blackPawnResource));
		pieceImages.put(new Bishop(Color.BLACK), createBufferedImage(blackBishopResource));
		pieceImages.put(new Rook(Color.BLACK), createBufferedImage(blackRookResource));
		pieceImages.put(new Knight(Color.BLACK), createBufferedImage(blackKnightResource));
		pieceImages.put(new King(Color.BLACK), createBufferedImage(blackKingResource));
		pieceImages.put(new Queen(Color.BLACK), createBufferedImage(blackQueenResource));
		
		return pieceImages;
	}

	private BufferedImage createBufferedImage(Resource resource) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(resource.getInputStream());
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
		}
		return image;
	}

	public void setBoardResource(Resource boardResource) {
		this.boardResource = boardResource;
	}

	public void setWhitePawnResource(Resource whitePawnResource) {
		this.whitePawnResource = whitePawnResource;
	}

	public void setWhiteBishopResource(Resource whiteBishopResource) {
		this.whiteBishopResource = whiteBishopResource;
	}

	public void setWhiteRookResource(Resource whiteRookResource) {
		this.whiteRookResource = whiteRookResource;
	}

	public void setWhiteKnightResource(Resource whiteKnightResource) {
		this.whiteKnightResource = whiteKnightResource;
	}

	public void setWhiteKingResource(Resource whiteKingResource) {
		this.whiteKingResource = whiteKingResource;
	}

	public void setWhiteQueenResource(Resource whiteQueenResource) {
		this.whiteQueenResource = whiteQueenResource;
	}

	public void setBlackPawnResource(Resource blackPawnResource) {
		this.blackPawnResource = blackPawnResource;
	}

	public void setBlackBishopResource(Resource blackBishopResource) {
		this.blackBishopResource = blackBishopResource;
	}

	public void setBlackRookResource(Resource blackRookResource) {
		this.blackRookResource = blackRookResource;
	}

	public void setBlackKnightResource(Resource blackKnightResource) {
		this.blackKnightResource = blackKnightResource;
	}

	public void setBlackKingResource(Resource blackKingResource) {
		this.blackKingResource = blackKingResource;
	}

	public void setBlackQueenResource(Resource blackQueenResource) {
		this.blackQueenResource = blackQueenResource;
	}
	
}
