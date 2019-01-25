package com.gestion.rh.result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.rh.candidate.Candidate;
import com.gestion.rh.employee.EmployeeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/result")
public class ResultRest {

	@Autowired
	private ResultService service;
	
	@Autowired
	private EmployeeService empService;
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ResponseEntity<Void> saveResults(@RequestBody List<Result> results){
		boolean saved = service.saveResults(results);
		boolean notified = empService.notifyResultQuizz(results.get(0).getCandidate(), results.get(0).getQuestion().getPart().getQuiz());
		return saved == false || notified == false ? new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/candidates/{quizzId}", method=RequestMethod.GET)
	public ResponseEntity<List<Candidate>> getCandidatesByQuizz(@PathVariable("quizzId") long quizzId){
		List<Candidate> candidates = service.getCandidatesByQuizz(quizzId);
		return candidates == null ? new ResponseEntity<List<Candidate>>(candidates, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}
	
	@RequestMapping(value="/candidate/{candidateId}/quizz/{quizzId}", method=RequestMethod.GET)
	public ResponseEntity<List<Object>> getCandidateResults(@PathVariable("candidateId") long candidateId, @PathVariable("quizzId") long quizzId){
		List<Object> results = service.getCandidateResults(candidateId, quizzId);
		System.out.println(results);
		return results == null ? new ResponseEntity<List<Object>>(results, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Object>>(results, HttpStatus.OK);
	}
}
