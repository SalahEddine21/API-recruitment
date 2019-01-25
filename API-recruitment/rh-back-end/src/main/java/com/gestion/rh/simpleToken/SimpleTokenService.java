package com.gestion.rh.simpleToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleTokenService {

	@Autowired
	private SimpleTokenRepository repo;
	
	public SimpleToken saveToken(SimpleToken token) {
		try {
			return repo.save(token);
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
	
	public SimpleToken getTokenByValue(String tokenValue) {
		try {
			return repo.getTokenByValue(tokenValue);
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
}
