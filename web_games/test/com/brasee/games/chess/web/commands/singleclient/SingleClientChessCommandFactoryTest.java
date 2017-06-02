package com.brasee.games.chess.web.commands.singleclient;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.brasee.games.chess.web.commands.singleclient.InvalidSingleClientCommand;
import com.brasee.games.chess.web.commands.singleclient.MoveSingleClientCommand;
import com.brasee.games.chess.web.commands.singleclient.PromoteSingleClientCommand;
import com.brasee.games.chess.web.commands.singleclient.ResetSingleClientGameCommand;
import com.brasee.games.chess.web.commands.singleclient.RetrieveSingleClientGameCommand;
import com.brasee.games.chess.web.commands.singleclient.SingleClientChessCommand;
import com.brasee.games.chess.web.commands.singleclient.SingleClientChessCommandFactory;

import static org.junit.Assert.*;

public class SingleClientChessCommandFactoryTest {

	@Test
	public void testFactoryReturnsRetrieveGameCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "retrieve_game");
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof RetrieveSingleClientGameCommand);
	}
	
	@Test
	public void testFactoryReturnsResetGameCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "reset_game");
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof ResetSingleClientGameCommand);
	}
	
	@Test
	public void testFactoryReturnsMoveCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "move");
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof MoveSingleClientCommand);
	}
	
	@Test
	public void testFactoryReturnsPromoteCommand() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "promote");
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof PromoteSingleClientCommand);
	}
	
	@Test
	public void testFactoryReturnsInvalidCommandForEmptyString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "");
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof InvalidSingleClientCommand);
	}
	
	@Test
	public void testFactoryReturnsInvalidCommandForNonsenseString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("command", "nonsense!");
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof InvalidSingleClientCommand);
	}
	
	@Test
	public void testFactoryReturnsInvalidCommandWhenNoCommandInRequest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		SingleClientChessCommand command = SingleClientChessCommandFactory.createCommand(request);
		assertTrue(command instanceof InvalidSingleClientCommand);
	}
}
