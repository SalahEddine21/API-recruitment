package com.gestion.rh.candidate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gestion.rh.mailing.EmailService;
import com.gestion.rh.quiz.Quiz;

@Service
public class CandidateService {

	@Autowired
	private CandidateRepository candidateRepo;
	
	@Autowired
	private EmailService emailService;

	@Value("${angular.url}")
	private String frontUrl;
	
	@Value("${takeQuizz.url}")
	private String quizzUrl;
	
	public Candidate getCandidateByUsername(String username) {
		try {
			return candidateRepo.getCandidateByUsername(username);
		} catch (Exception e) {
			System.out.println("EXCEPTION IS: "+e.getMessage());
			return null;
		}
	}
	
	public boolean convocateCandidate(String subject, String to, String body) {
		return emailService.sendEmail(to, subject, body);
	}
	
	public Candidate getCandidateById(long candidateId) {
		try {
			return candidateRepo.getOne(candidateId);
		} catch (Exception e) {
			System.out.println("EXCEPTION IS: "+e.getMessage());
			return null;
		}
	}
	
	public List<Candidate> getAllCandidates(){
		try {
			return candidateRepo.findAll();
		} catch (Exception e) {
			System.out.println("EXCEPTION IS: "+e.getMessage());
			return null;
		}
	}
	
	public boolean mailCandidate(Map<String, String> data) {
		String subject = (String) data.get("subject");
		String from = (String) data.get("from");
		String to = (String) data.get("to");
		String body = (String) data.get("body");
		
		return emailService.mailCandidate(subject, from, to, body);
	}
	
	public boolean convocateQuizz(long quizzId, Candidate candidate, String tokenValue, Quiz quizz) {
		String subject = "Quizz Examination";
		String to = candidate.getEmail();
		String body = "Hello Mr, "+candidate.getLastname()+System.lineSeparator();
		body = body + "You are convocated by " + quizz.getEmployee().getEntreprise().getName() + " company " 
		+ " to passe a test quizz in " + quizz.getTechnlogie() + " , follow the link below" + System.lineSeparator();
		String urltest = frontUrl + quizzUrl + tokenValue + "/" + quizzId;
		body = body + urltest;
		body = body + " , you have 48h to pass it, other ways the access will be closed." + System.lineSeparator();
		body = body + "Have a nice day";
		return emailService.sendEmail(to, subject, body);
	}
}
