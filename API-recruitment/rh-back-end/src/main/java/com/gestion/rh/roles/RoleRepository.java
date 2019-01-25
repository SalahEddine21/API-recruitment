package com.gestion.rh.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("select r from Role r where r.role=:x")
	public Role searchByName(@Param("x") String role);
}
