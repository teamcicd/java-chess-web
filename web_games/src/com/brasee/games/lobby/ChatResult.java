package com.brasee.games.lobby;

import java.util.List;

public class ChatResult<T> {

	private int nextMessageIndex;
	private List<T> messages;
	
	public ChatResult(int nextMessageIndex, List<T> messages) {
		this.nextMessageIndex = nextMessageIndex;
		this.messages = messages;
	}
	
	public int getNextMessageIndex() {
		return nextMessageIndex;
	}
	
	public List<T> getMessages() {
		return messages;
	}
}
