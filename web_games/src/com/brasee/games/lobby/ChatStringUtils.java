package com.brasee.games.lobby;

public class ChatStringUtils {

	private static int MAX_MESSAGE_LENGTH = 150;
	
	public static String escapeAndLimitString(String message) {
		String newMessage = null;
		if (message != null) {
			newMessage = new String(message);	
			newMessage = escapeMarkupChars(newMessage);
			newMessage = limitMessageLength(newMessage);
		}
		return newMessage;
	}
	
	public static String escapeMarkupChars(String message) {
		String newMessage = null;
		if (message != null) {
			newMessage = new String(message);
			newMessage = newMessage.replace("<", "&lt;");
			newMessage = newMessage.replace(">", "&gt;");
			newMessage = newMessage.replace("&", "&amp;");
			newMessage = newMessage.replaceAll("[^\\p{ASCII}]", "");

		}
		return newMessage;
	}
	
	public static String limitMessageLength(String message) {
		String newMessage = null;
		if (message != null) {
			newMessage = new String(message);
			if (newMessage.length() > MAX_MESSAGE_LENGTH) {
				newMessage = newMessage.substring(0, MAX_MESSAGE_LENGTH);
			}
		}
		return newMessage;
	}
	
}
