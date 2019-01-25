package com.gestion.rh.candidature;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/candidature")
public class CandidatureRest {
	
	@Autowired
	private CandidatureService service;
	
	@RequestMapping(value="/offre/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Candidature>> getCandidatures(@PathVariable("id") long offreId){
		List<Candidature> candidatures = service.getCandidaturesByOffre(offreId);
		return candidatures == null ? new ResponseEntity<List<Candidature>>(candidatures, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Candidature>>(candidatures, HttpStatus.OK);
	}
	
	@RequestMapping(value="/candidate/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Candidature>> getCandidaturesByCandidate(@PathVariable("id") long candidateId ){
		List<Candidature> candidatures = service.getCandidaturesByCandidate(candidateId);
		return candidatures == null ? new ResponseEntity<List<Candidature>>(candidatures, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Candidature>>(candidatures, HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> cancelCandidature(@PathVariable("id") long candidatureId){
		boolean deleted = service.deleteCandidature(candidatureId);
		return deleted == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}

}
