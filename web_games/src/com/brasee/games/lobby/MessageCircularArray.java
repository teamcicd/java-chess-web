package com.brasee.games.lobby;

import java.lang.reflect.Array;
import java.util.LinkedList;

public class MessageCircularArray<T> {

	private T[] messages;
	private int nextMessageIndex;
	
	@SuppressWarnings("unchecked")
	public MessageCircularArray (int arraySize, Class<T> klass) {
		messages = (T[])Array.newInstance(klass, arraySize);
	}

	public int getMessageIndex() {
		return nextMessageIndex;
	}
	
	public ChatResult<T> getMessages(int previousNextMessageIndex, int maxMessagesToReturn) {
		LinkedList<T> newMessages = new LinkedList<T>();
		int newNextMessageIndex = this.nextMessageIndex;
		
		if (isValidIndex(previousNextMessageIndex)) {
			int i = 0;
			int iterNextMessageIndex = newNextMessageIndex;
			while (iterNextMessageIndex != previousNextMessageIndex && i < maxMessagesToReturn) {
				iterNextMessageIndex = decrementMessageIndex(iterNextMessageIndex);
				if (this.messages[iterNextMessageIndex] != null) {
					newMessages.addFirst(this.messages[iterNextMessageIndex]);
				}
				i++;
			}
		}
		
		return new ChatResult<T>(newNextMessageIndex, newMessages);
	}
	
	public void addMessage(T message) {
		if (message != null) {
			int newMessageIndex = incrementMessageIndex(this.nextMessageIndex);
			this.messages[nextMessageIndex] = message;
			nextMessageIndex = newMessageIndex;
		}
	}
	
	private int decrementMessageIndex(int messageIteratorIndex) {
		int nextMessageIteratorIndex;
		
		if (messageIteratorIndex == 0) {
			nextMessageIteratorIndex = this.messages.length - 1;
		}
		else {
			nextMessageIteratorIndex = messageIteratorIndex - 1;	
		}
		
		return nextMessageIteratorIndex;
	}
	
	private int incrementMessageIndex(int messageIteratorIndex) {
		int nextMessageIteratorIndex;
		
		if (messageIteratorIndex == this.messages.length - 1) {
			nextMessageIteratorIndex = 0;
		}
		else {
			nextMessageIteratorIndex = messageIteratorIndex + 1;
		}
		
		return nextMessageIteratorIndex;
	}

	private boolean isValidIndex(int previousIndex) {
		return previousIndex >= 0 && previousIndex < this.messages.length;
	}
}
