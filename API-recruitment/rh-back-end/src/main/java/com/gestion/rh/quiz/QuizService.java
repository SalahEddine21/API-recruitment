package com.gestion.rh.quiz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.rh.employee.Employee;
import com.gestion.rh.models.ApplicationContextProvider;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepo;
	
	@Autowired
	private PartRepository partRepo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	public boolean addQuizz(Map<String, Object> quizz, Employee emp) {
		try {
			Quiz quiz = (Quiz) provider.getApplicationContext().getBean("quiz", Quiz.class);
			quiz.setTitle((String) quizz.get("title"));
			quiz.setTechnology((String) quizz.get("technology"));
			quiz.setEmployee(emp);
			quiz = quizRepo.save(quiz); // save the quizz 
			
			Part pt;
			Question qt;
			List<Map<String, Object>> parts = (List<Map<String, Object>>) quizz.get("parts");
			
			for (Map<String, Object> part : parts) {
				pt = (Part) provider.getApplicationContext().getBean("part", Part.class);
				pt.setTitle((String) part.get("title"));
				pt.setQuiz(quiz);
				pt = partRepo.save(pt); // save the part
				
				List<Map<String, Object>> questions = (List<Map<String, Object>>) part.get("questions"); 
				for(Map<String, Object> question : questions) {
					qt = (Question) provider.getApplicationContext().getBean("question", Question.class);
					qt.setQuestion((String) question.get("content"));
					qt.setAnswer((boolean) question.get("answer"));
					qt.setPart(pt);
					questionRepo.save(qt); // save each question
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
			return false;
		}
	}
	
	public List<Quiz> getQuizzByEmployee(long employeeId){
		try {
			return quizRepo.getQuizzByEmplyee(employeeId);
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
			return null;
		}
	}
	
	public List<Question> getFullQuizz(long quizzId){
		try {
			return quizRepo.getFullQuizz(quizzId);
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
			return null;
		}
	}
	
	public Quiz getQuizz(long quizzId) {
		try {
			return quizRepo.getOne(quizzId);
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
			return null;
		}
	}
	
	public List<Quiz> getCandidateQuizz(long candidateId){
		try {
			return quizRepo.getCandidateQuizz(candidateId);
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
			return null;
		}
	}
}
