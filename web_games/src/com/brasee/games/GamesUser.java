package com.brasee.games;

public class GamesUser {

	private String name;
	private String sessionId;

	public GamesUser(String name, String sessionId) {
		this.name = name;
		this.sessionId = sessionId;
	}
	
	public String getName() {
		return name;
	}

	public String getSessionId() {
		return sessionId;
	}
	
}
