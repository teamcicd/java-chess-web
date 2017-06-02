package com.brasee.games.lobby;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.brasee.chess.Game;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public class GamePreviewImageGenerator {

	public static final int BOARD_SIZE = 200;
	public static final int SQUARE_SIZE = 25;
	public static final String IMAGE_FORMAT = "PNG";
	
	private BufferedImage boardImage;
	private Map<Piece, BufferedImage> pieceImages;
	
	public GamePreviewImageGenerator(BufferedImage boardImage, Map<Piece, BufferedImage> pieceImages) {
		this.boardImage = boardImage;
		this.pieceImages = pieceImages;
	}
	
	/**
	 * Generate a PNG image representing the current state of board.
	 *  
	 * @param game
	 */
	public BufferedImage createPngPreviewImage(Game game) {
	    BufferedImage image = new BufferedImage(BOARD_SIZE, BOARD_SIZE, BufferedImage.TYPE_INT_ARGB); 
	
	    Graphics2D g2d = image.createGraphics();
	    AffineTransformOp transformOp = new AffineTransformOp(new AffineTransform(), AffineTransformOp.TYPE_BILINEAR);
	    g2d.drawImage(this.boardImage, transformOp, 0, 0);
	    Map<Square, Piece> pieceMap = game.pieces();
	    for (Square square : pieceMap.keySet()) {
	    	int xPos = SQUARE_SIZE * (square.file() - 'a');
	    	int yPos = SQUARE_SIZE * ('8' - square.rank());
	    	BufferedImage pieceImage = this.pieceImages.get(pieceMap.get(square));
	    	g2d.drawImage(pieceImage, transformOp, xPos, yPos);
	    }
   
	    return image;
	}
	
}
