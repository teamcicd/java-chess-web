package com.brasee.games.lobby;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.brasee.chess.Game;
import com.brasee.chess.Square;

public class GamePreviewImageGeneratorTest {

	private GamePreviewImageGeneratorFactory factory;
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
		factory = new GamePreviewImageGeneratorFactory();
		ApplicationContext context = new FileSystemXmlApplicationContext();

		factory.setBoardResource(context.getResource("war/img/board_small.png"));
		factory.setWhitePawnResource(context.getResource("war/img/pawn_white_small.png"));
		factory.setWhiteBishopResource(context.getResource("war/img/bishop_white_small.png"));
		factory.setWhiteKnightResource(context.getResource("war/img/knight_white_small.png"));
		factory.setWhiteRookResource(context.getResource("war/img/rook_white_small.png"));
		factory.setWhiteKingResource(context.getResource("war/img/king_white_small.png"));
		factory.setWhiteQueenResource(context.getResource("war/img/queen_white_small.png"));		
		factory.setBlackPawnResource(context.getResource("war/img/pawn_black_small.png"));
		factory.setBlackBishopResource(context.getResource("war/img/bishop_black_small.png"));
		factory.setBlackKnightResource(context.getResource("war/img/knight_black_small.png"));
		factory.setBlackRookResource(context.getResource("war/img/rook_black_small.png"));
		factory.setBlackKingResource(context.getResource("war/img/king_black_small.png"));
		factory.setBlackQueenResource(context.getResource("war/img/queen_black_small.png"));
	}
	
	@Test
	public void testImageGeneratedForEmptyGame() {
		GamePreviewImageGenerator generator = factory.getInstance();
		@SuppressWarnings("unused")
		BufferedImage image = generator.createPngPreviewImage(game);
		//testFileOutput(image);
	}

	@Test
	public void testImageGeneratedForInitializedGame() {
		GamePreviewImageGenerator generator = factory.getInstance();
		game.initializeBoard();
		@SuppressWarnings("unused")
		BufferedImage image = generator.createPngPreviewImage(game);
		//testFileOutput(image);
	}
	
	@Test
	public void testImageGeneratedForGameWithMove() {
		GamePreviewImageGenerator generator = factory.getInstance();
		game.initializeBoard();
		game.move(new Square("a2"), new Square("a4"));
		@SuppressWarnings("unused")
		BufferedImage image = generator.createPngPreviewImage(game);
		//testFileOutput(image);
	}
	
	@SuppressWarnings("unused")
	private void testFileOutput(BufferedImage image) {
		try {
			File file = new File("d:/tempfile.png");
			ImageIO.write(image, "PNG", file);
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
		}
	}
	
}
