package com.brasee.games.lobby;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

public class UserManagerExpiryTimer extends TimerTask {

	private long expiryTimeInMillis;
	private Map<String, String> userNameMap;
	private Map<String, Long> userRefreshMap;
	
	public UserManagerExpiryTimer(long expiryTimeInMillis, Map<String, String> userNameMap, Map<String, Long> userRefreshMap) {
		this.expiryTimeInMillis = expiryTimeInMillis;
		this.userNameMap = userNameMap;
		this.userRefreshMap = userRefreshMap;
	}
	
	@Override
	public void run() {
		Long currentTime = System.currentTimeMillis();
		
		Set<String> sessionIds = new HashSet<String>();
		synchronized(userRefreshMap) {
			for (String sessionId : userRefreshMap.keySet()) {
				sessionIds.add(new String(sessionId));
			}
		}

		for (String sessionId : sessionIds) {
			Long lastRefreshTime = userRefreshMap.get(sessionId);
			if (lastRefreshTime == null || 
					currentTime.longValue() - lastRefreshTime.longValue() > expiryTimeInMillis) {
				userNameMap.remove(sessionId);
				userRefreshMap.remove(sessionId);
			}
		}
	}

}
