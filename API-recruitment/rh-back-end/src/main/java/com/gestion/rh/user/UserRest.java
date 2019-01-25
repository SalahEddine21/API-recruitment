package com.gestion.rh.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserRest {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public ResponseEntity<User> signUp(@RequestBody User user){
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.saveUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") long id){
		User user = userService.getUser(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/current", method=RequestMethod.GET)
	public ResponseEntity<User> getCurrent(Authentication authentication){
		//System.out.println(authentication.getName());
		User user = userService.getUser(authentication.getName()); // get the current user from username stored in the authentication object
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
}
