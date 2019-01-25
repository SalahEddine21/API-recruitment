package com.gestion.rh.result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestion.rh.candidate.Candidate;

@Service
public class ResultService {

	@Autowired
	private ResultRepository repo;
	
	public boolean saveResults(List<Result> results) {
		try {
			repo.saveAll(results);
			return true;
		} catch (Exception e) {
			System.out.println("Exception during saving result :" +e.getMessage());
			return false;
		}
	}
	
	public List<Candidate> getCandidatesByQuizz(long quizzId){
		try {
			return repo.getCandidatesByQuizz(quizzId);
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
	
	public List<Object> getCandidateResults(long candidateId, long quizzId){
		try {
			return repo.getCandidateResults(candidateId, quizzId);
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
}
