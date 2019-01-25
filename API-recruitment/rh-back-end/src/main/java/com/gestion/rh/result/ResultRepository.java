package com.gestion.rh.result;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion.rh.candidate.Candidate;

public interface ResultRepository extends JpaRepository<Result, Long> {

	@Query("select r.candidate from Result r where r.question.part.quiz.id=:x group by r.candidate")
	public List<Candidate> getCandidatesByQuizz(@Param("x") long quizzId);
	
	@Query("select r.question.part.title, sum(r.result), avg(r.result) from Result r where r.candidate.id=:x and r.question.part.quiz.id=:y group by r.question.part")
	public List<Object> getCandidateResults(@Param("x") long candidateId, @Param("y") long quizzId);
}
 