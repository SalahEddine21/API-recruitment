package com.gestion.rh.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gestion.rh.candidate.Candidate;
import com.gestion.rh.mailing.EmailService;
import com.gestion.rh.quiz.Quiz;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${angular.url}")
	private String frontUrl;
	
	@Value("${results.url}")
	private String resultsUrl;
	
	public Employee saveEmployee(Employee emp) {
		try {
			empRepo.save(emp);
			return emp;
		} catch (Exception e) {
			System.out.println("save Employee EXCEPTION: "+e.getMessage());
			return null;		}
	}
	
	public Employee getEmployee(long id) {
		try {
			return empRepo.getOne(id);
		} catch (Exception e) {
			System.out.println("get Employee by id EXCEPTION: "+e.getMessage());
			return null;
		}
	}
	
	public Employee getEmployeeByUsername(String username) {
		try {
			return empRepo.getEmployeeByUsername(username);
		} catch (Exception e) {
			System.out.println("get Employee by username EXCEPTION: "+e.getMessage());
			return null;
		}
	}
	
	public boolean notifyResultQuizz(Candidate candidate, Quiz quizz) {
		try {
			String subject = "Result of Quizz "+quizz.getTitle();
			String to = quizz.getEmployee().getEmail();
			String body = "Hello "+quizz.getEmployee().getName()+System.lineSeparator();
			body = body + "Candidate "+candidate.getName()+" "+candidate.getLastname()+" took the quizz "
			+quizz.getTitle()+" follow the link below to see results."+System.lineSeparator();
			String url = frontUrl + candidate.getId() + "/" + quizz.getId();
			body = body + url;
			emailService.sendEmail(to, subject, body);
			return true;
		} catch (Exception e) {
			System.out.println("Exception is: "+e.getMessage());
			return false;
		}
	}
}
