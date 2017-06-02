package com.brasee.games.lobby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ChatManagerTest {
	
	private static int MESSAGE_ARRAY_SIZE = 100;
	private static int MAX_MESSAGES_RETURNED = 100;
	
	private ChatManager manager;
	
	@Before
	public void setUp() {
		manager = new ChatManager(MESSAGE_ARRAY_SIZE, MAX_MESSAGES_RETURNED);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testChatManagerMessageArraySizeMustBeGreaterThan1() {
		new ChatManager(1, MAX_MESSAGES_RETURNED);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testChatManagerMaxMessagesReturnedMustBeGreaterThan0() {
		new ChatManager(MESSAGE_ARRAY_SIZE, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testChatManagerMaxMessagesReturnedCannotBeGreaterThanMessageArraySize() {
		new ChatManager(MESSAGE_ARRAY_SIZE, MESSAGE_ARRAY_SIZE + 1);
	}
	
	@Test
	public void testUsingOutOfBoundsPreviousIndexReturnsNoMessages() {
		manager.addMessage("test message", 0);
		ChatResult<String> chatResult = manager.getMessages(-1);
		assertTrue(chatResult.getMessages().isEmpty());
	}
	
	@Test
	public void testChatManagerReturnsCorrectMessageIndex() {
		assertEquals(new Integer(0), manager.getMessageIndex());
		manager.addMessage("test message", 0);
		assertEquals(new Integer(1), manager.getMessageIndex());
	}
	
	@Test
	public void testChatManagerReturnsCorrectSingleMessage() {
		int previousIndex = manager.getMessageIndex();
		ChatResult<String> chatResult = manager.addMessage("test message", previousIndex);
		assertEquals(new Integer(1), new Integer(chatResult.getMessages().size()));
		assertEquals(new Integer(1), new Integer(chatResult.getNextMessageIndex()));
		assertEquals("test message", chatResult.getMessages().get(0));
	}
	
	@Test
	public void testChatManagerReturnsCorrectMultipleMessages() {
		int previousIndex = manager.getMessageIndex();
		manager.addMessage("test message 1", previousIndex);
		manager.addMessage("test message 2", previousIndex + 1);
		ChatResult<String> chatResult = manager.getMessages(previousIndex);
		assertEquals(new Integer(2), new Integer(chatResult.getMessages().size()));
		assertEquals(new Integer(2), new Integer(chatResult.getNextMessageIndex()));
		assertEquals("test message 1", chatResult.getMessages().get(0));
		assertEquals("test message 2", chatResult.getMessages().get(1));
	}
	
	@Test
	public void testChatManagerWorksCorrectlyWhenCrossingInteralMessageArrayBounds() {
		ChatManager newManager = new ChatManager(3, 2);
		newManager.addMessage("test message 1", 0);
		ChatResult<String> chatResult = newManager.addMessage("test message 2", 1);
		int previousIndex = chatResult.getNextMessageIndex();
		newManager.addMessage("test message 3", 2);
		chatResult = newManager.addMessage("test message 4", previousIndex);
		assertEquals(new Integer(2), new Integer(chatResult.getMessages().size()));
		assertEquals(new Integer(1), new Integer(chatResult.getNextMessageIndex()));
		assertEquals("test message 3", chatResult.getMessages().get(0));
		assertEquals("test message 4", chatResult.getMessages().get(1));
	}
	
}