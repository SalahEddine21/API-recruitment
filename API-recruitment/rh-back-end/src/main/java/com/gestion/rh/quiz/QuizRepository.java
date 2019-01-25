package com.gestion.rh.quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

	@Query("select q from Quiz q where q.employee.id=:x")
	public List<Quiz> getQuizzByEmplyee(@Param("x") long employeeid);
	
	@Query("select q from Question q where q.part.quiz.id=:x")
	public List<Question> getFullQuizz(@Param("x") long quizzId);
	
	@Query("select r.question.part.quiz from Result r where r.candidate.id=:x group by r.question.part.quiz")
	public List<Quiz> getCandidateQuizz(@Param("x") long candidateId);
}
