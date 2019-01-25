
package com.gestion.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gestion.rh.models.ApplicationContextProvider;
import com.gestion.rh.roles.Role;
import com.gestion.rh.roles.RoleRepository;

@SpringBootApplication
public class RhBackEndApplication implements ApplicationRunner{

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	public static void main(String[] args) {
		SpringApplication.run(RhBackEndApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Role> roles = roleRepo.findAll();
		if(roles.isEmpty()) { // add roles if they do not exist
			Role role = (Role) provider.getApplicationContext().getBean("role", Role.class);
			role.setRole("EMPLOYEE_ROLE");
			role.setDescription("Role for the employee espace");
			roleRepo.save(role);
			
			role = (Role) provider.getApplicationContext().getBean("role", Role.class);
			role.setRole("CANDIDATE_ROLE");
			role.setDescription("Role for the candidate espace");
			roleRepo.save(role);
		}
	}
	
	
}
