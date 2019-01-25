package com.gestion.rh.candidate.cv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CvReporsitory extends JpaRepository<Cv, Long>{

	@Query("select c from Cv c where c.id=:x")
	public Cv getCvByID(@Param("x")long cvId);
	
	@Query("select c from Cv c where c.candidate.id=:x")
	public List<Cv> getCvsBYcandidate(@Param("x")long candidateId);
	
	@Query("select c from Cv c where c.candidate.id=:x and c.hidden=0")
	public Cv getVisibleCv(@Param("x")long candidateId);
}
