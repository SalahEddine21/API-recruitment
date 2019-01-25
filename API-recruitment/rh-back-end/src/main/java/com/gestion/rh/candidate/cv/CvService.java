package com.gestion.rh.candidate.cv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CvService {

	@Autowired
	private CvReporsitory cvRepo;
	
	public Cv getCvCandidate(long id) {
		try {
			Cv cv = cvRepo.getCvByID(id);
			return cv;
		} catch (Exception e) {
			System.out.println("EXCEPTION : "+e.getMessage());
			return null;
		}
	}
	
	public List<Cv> getCvsBYcandidate(long candidateId){
		try {
			return cvRepo.getCvsBYcandidate(candidateId);
		} catch (Exception e) {
			System.out.println("EXCEPTION : "+e.getMessage());
			return null;
		}
	}
	
	public Cv getVisibleCv(long candidateId) {
		try {
			return cvRepo.getVisibleCv(candidateId);
		} catch (Exception e) {
			System.out.println("EXCEPTION : "+e.getMessage());
			return null;
		}
	}
	
	public boolean deleteCvById(long cvId) {
		try {
			cvRepo.deleteById(cvId);
			return true;
		} catch (Exception e) {
			System.out.println("EXCEPTION : "+e.getMessage());
			return false;
		}
	}
	
	public Cv getCvById(long cvId) {
		try {
			return cvRepo.getCvByID(cvId);
		} catch (Exception e) {
			System.out.println("EXCEPTION : "+e.getMessage());
			return null;
		}
	}
	
	public Cv saveCv(Cv c) {
		try {
			return cvRepo.save(c);
		} catch (Exception e) {
			System.out.println("EXCEPTION : "+e.getMessage());
			return null;
		}
	}
}
