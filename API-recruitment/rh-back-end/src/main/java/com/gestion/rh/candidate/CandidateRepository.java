package com.gestion.rh.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateRepository extends JpaRepository<Candidate, Long>{

	@Query("select c from Candidate c where c.username=:x")
	public Candidate getCandidateByUsername(@Param("x") String username);
}
