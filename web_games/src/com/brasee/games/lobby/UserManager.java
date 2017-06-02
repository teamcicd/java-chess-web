package com.brasee.games.lobby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
	
	/** Maps the sessionId to the user's name */
	private Map<String, String> userNameMap;
	
	/** Maps the sessionId to the time it last sent a refresh */
	private Map<String, Long> userRefreshMap;
	
	/** Timer to perform the refresh to detect expired users */
	private Timer userRefreshTimer;
	
	public UserManager(long expiryTimeInMillis, long refreshTimeInMillis) {
		this.userNameMap = new ConcurrentHashMap<String, String>();
		this.userRefreshMap = new ConcurrentHashMap<String, Long>();
		
		this.userRefreshTimer = new Timer(true);
		this.userRefreshTimer.scheduleAtFixedRate(new UserManagerExpiryTimer(expiryTimeInMillis, userNameMap, userRefreshMap), 
			0, refreshTimeInMillis);
	}
	
	/**
	 * Returns true if the given sessionId is connected to the game, otherwise returns false.
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean isConnected(String sessionId) {
		return userNameMap.containsKey(sessionId);
	}
	
	/**
	 * Indicates that this user is connected at the current time.
	 * 
	 * @param sessionId
	 * @param userName
	 * @return
	 */
	public void refreshUser(String sessionId, String userName) {
		Long currentTime = System.currentTimeMillis();
		synchronized(userRefreshMap) {
			userNameMap.put(sessionId, userName);
			userRefreshMap.put(sessionId, currentTime);
		}
	}
	
	/**
	 * Retrieve the userNames of all connected users.
	 *   
	 * @return
	 */
	public List<String> getCurrentUserNames() {
		List<String> currentUserNames = new ArrayList<String>();
		
		synchronized(userRefreshMap) {
			for (String sessionId : userRefreshMap.keySet()) {
				currentUserNames.add(new String(userNameMap.get(sessionId)));
			}
		}

		Collections.sort(currentUserNames);
		return currentUserNames;
	}
	
}
