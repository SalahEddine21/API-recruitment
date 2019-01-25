package com.gestion.rh.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("select e from Employee e where e.username=:x")
	public Employee getEmployeeByUsername(@Param("x") String username);
	
	@Query("select e from Employee e where e.id=:x")
	public Employee getEmployeeByID(@Param("x") long id);
}
