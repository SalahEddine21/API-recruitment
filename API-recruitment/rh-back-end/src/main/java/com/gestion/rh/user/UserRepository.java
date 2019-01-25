package com.gestion.rh.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

	@Query("select u from User u where u.username=:x")
	public User findByUsername(@Param("x") String username);
}
