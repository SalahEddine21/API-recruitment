package com.gestion.rh.candidature;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

	@Query("select c from Candidature c where c.offre.id=:x")
	public List<Candidature> getCandidaturesByOffre(@Param("x")long offreId);
	
	@Query("select c from Candidature c where c.candidate.id=:x")
	public List<Candidature> getCandidaturesByCandidate(@Param("x")long candidateId);
}
