package com.gestion.rh.candidature;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatureService {

	@Autowired
	private CandidatureRepository candidaturesRepo;
	
	public List<Candidature> getCandidaturesByOffre(long offreId){
		try {
			List<Candidature> candidatures = candidaturesRepo.getCandidaturesByOffre(offreId);
			return candidatures;
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
	
	public List<Candidature> getCandidaturesByCandidate(long candidateId){
		try {
			List<Candidature> candidatures = candidaturesRepo.getCandidaturesByCandidate(candidateId);
			return candidatures;
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
	
	public boolean deleteCandidature(long candidatureId) {
		try {
			candidaturesRepo.deleteById(candidatureId);
			return true;
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return false;
		}
	}
}
