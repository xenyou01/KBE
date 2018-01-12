package de.htwBerlin.ai.kbe.storage;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

public class Token {
	private static Token instance;
	private ConcurrentHashMap<String, String> tokens;
	
	private Token() {
		tokens = new ConcurrentHashMap<>();
	}
	
	public static Token getInstance() {
		if (instance == null)
			instance = new Token();
		return instance;
	}
	
	public String createToken(String userId) {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[200];
		random.nextBytes(bytes);
		String token = bytes.toString();
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
