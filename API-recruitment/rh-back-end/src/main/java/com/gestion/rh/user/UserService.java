package com.gestion.rh.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public User getSudo(long id) {
		try {
			User u = userRepo.getOne(id);
			return u;
		} catch (Exception e) {
			System.out.println("Exception is: "+e.getMessage());
			return null;
		}
	}
	
	public User saveUser(User user) {
		userRepo.save(user);
		return user;
	}
	
	public User getUser(long id) {
		try {
			User u = userRepo.getOne(id);
			return u;
		} catch (Exception e) {
			System.out.println("Exception is: "+e.getMessage());
			return null;
		}
	}
	
	public User getUser(String username) {
		try {
			User u = userRepo.findByUsername(username);
			return u;
		} catch (Exception e) {
			System.out.println("Exception is: "+e.getMessage());
			return null;
		}
	}	
}
