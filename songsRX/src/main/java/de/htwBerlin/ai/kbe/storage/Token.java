package de.htwBerlin.ai.kbe.storage;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Token {
	private static Token instance;
	private ConcurrentHashMap<String, String> tokens;
	
	private Token() {
		tokens = new ConcurrentHashMap<>();
		tokens.put("me", "[B@521d7602");
	}
	
	public static Token getInstance() {
		if (instance == null)
			instance = new Token();
		return instance;
	}
	
	public String createToken(String userId) {
		String token = UUID.randomUUID().toString();
		token = token.replaceAll("-", "");
		tokens.put(userId, token);
		return token;
	}
	
	public boolean isTokenvalid(String token) {
		return tokens.contains(token);
	}
	
	public String getToken(String userId) {
		return tokens.get(userId);
	}

}
