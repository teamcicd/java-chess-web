package com.brasee.games.chess.web.commands.multiclient;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.brasee.games.chess.web.commands.multiclient.InvalidMultiClientCommand;
import com.brasee.games.chess.web.commands.multiclient.MoveMultiClientCommand;
import com.brasee.games.chess.web.commands.multiclient.MultiClientChessCommand;
import com.brasee.games.chess.web.commands.multiclient.MultiClientChessCommandFactory;
import com.brasee.games.chess.web.commands.multiclient.PromoteMultiClientCommand;
import com.brasee.games.chess.web.commands.multiclient.ResetMultiClientGameCommand;
import com.brasee.games.chess.web.commands.multiclient.RetrieveMultiClientGameCommand;

public class MultiClientChessCommandFactoryTest {

	@Test
	public void testFactoryReturnsRetrieveGameCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "retrieve_game");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof RetrieveMultiClientGameCommand);
	}
	
	@Test
	public void testFactoryReturnsRetrieveGameMoveIndexCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "retrieve_game_status");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof RetrieveMultiClientGameStatusCommand);
	}
	
	@Test
	public void testFactoryReturnsResetGameCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "reset_game");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof ResetMultiClientGameCommand);
	}
	
	@Test
	public void testFactoryReturnsMoveCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "move");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof MoveMultiClientCommand);
	}
	
	@Test
	public void testFactoryReturnsPromoteCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "promote");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof PromoteMultiClientCommand);
	}
	
	@Test
	public void testFactoryReturnsInvalidCommandForEmptyString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof InvalidMultiClientCommand);
	}
	
	@Test
	public void testFactoryReturnsInvalidCommandForNonsenseString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "nonsense!");
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof InvalidMultiClientCommand);
	}
	
	@Test
	public void testFactoryReturnsInvalidCommandWhenNoCommandInRequest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MultiClientChessCommand command = MultiClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof InvalidMultiClientCommand);
	}
	
}
