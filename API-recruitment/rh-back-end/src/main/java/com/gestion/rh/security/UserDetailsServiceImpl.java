package com.gestion.rh.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.rh.candidate.Candidate;
import com.gestion.rh.candidate.CandidateService;
import com.gestion.rh.employee.Employee;
import com.gestion.rh.employee.EmployeeRepository;
import com.gestion.rh.employee.EmployeeService;
import com.gestion.rh.models.ApplicationContextProvider;
import com.gestion.rh.user.User;
import com.gestion.rh.user.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("the username sent is: "+username);
		
		//User user = userRepo.findByUsername(username);
		Employee emp = empService.getEmployeeByUsername(username);
		
		if(emp == null) {
			Candidate cnd = candidateService.getCandidateByUsername(username);
			if(cnd == null) throw new UsernameNotFoundException("User details not found with this username: "+username);
			System.out.println("IS CANDIDATE!");
			Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
			cnd.getRoles().forEach(r -> {
				authorities.add((SimpleGrantedAuthority) provider.getApplicationContext().getBean("simpleGrantedauthority", r.getRole()));
			});
			this.bCryptPasswordEncoder = (BCryptPasswordEncoder) provider.getApplicationContext().getBean("bcryptPasswordencoder", BCryptPasswordEncoder.class);
			return new org.springframework.security.core.userdetails.User(cnd.getUsername(), this.bCryptPasswordEncoder.encode(cnd.getPassword()), authorities);
		}
		
		System.out.println("IS EMPLOYEE!");
		Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		emp.getRoles().forEach(r -> {
			authorities.add((SimpleGrantedAuthority) provider.getApplicationContext().getBean("simpleGrantedauthority", r.getRole()));
		});
		this.bCryptPasswordEncoder = (BCryptPasswordEncoder) provider.getApplicationContext().getBean("bcryptPasswordencoder", BCryptPasswordEncoder.class);
		return new org.springframework.security.core.userdetails.User(emp.getUsername(), this.bCryptPasswordEncoder.encode(emp.getPassword()), authorities);
	}

}
