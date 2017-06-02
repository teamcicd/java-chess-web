package com.brasee.games.lobby;

public class ChatManager {
	
	private static int DEFAULT_MESSAGE_ARRAY_SIZE = 20;
	private static int DEFAULT_MAX_MESSAGES_TO_RETURN = 10;
	
	private static int MIN_MESSAGE_ARRAY_SIZE = 2;
	private static int MIN_MAX_MESSAGES_TO_RETURN = 1;
	
	private int maxMessagesToReturn;
	private MessageCircularArray<String> messageArray;
	
	public ChatManager() {
		this(DEFAULT_MESSAGE_ARRAY_SIZE, DEFAULT_MAX_MESSAGES_TO_RETURN);
	}
	
	public ChatManager(int messageArraySize, int maxMessagesToReturn) {
		if (messageArraySize < MIN_MESSAGE_ARRAY_SIZE) {
			throw new IllegalArgumentException("Message array size must be greater than or equal to " 
					+ MIN_MESSAGE_ARRAY_SIZE);
		}
		if (maxMessagesToReturn < MIN_MAX_MESSAGES_TO_RETURN) {
			throw new IllegalArgumentException("Maximum messages to return must be greater than or equal to " 
					+ MIN_MAX_MESSAGES_TO_RETURN);
		}
		if (maxMessagesToReturn > messageArraySize) {
			throw new IllegalArgumentException("Maximum messages to return must be less than or equal to "
					+ " message array size.");
		}
		
		this.maxMessagesToReturn = maxMessagesToReturn;
		this.messageArray = new MessageCircularArray<String>(messageArraySize, String.class);
	}
	
	public ChatResult<String> addMessage(String message, int previousNextMessageIndex) {
		ChatResult<String> chatResult = null;
		synchronized (messageArray) {
			messageArray.addMessage(message);
			chatResult = messageArray.getMessages(previousNextMessageIndex, maxMessagesToReturn);
		}
		return chatResult;
	}
	
	public ChatResult<String> getMessages(int previousNextMessageIndex) {
		ChatResult<String> chatResult = null;
		synchronized (messageArray) {
			chatResult = messageArray.getMessages(previousNextMessageIndex, maxMessagesToReturn);
		}
		return chatResult;
	}

	public Integer getMessageIndex() {
		Integer messageIndex = null;
		synchronized (messageArray) {
			messageIndex = messageArray.getMessageIndex();	
		}
		return messageIndex;
	}
	
}
