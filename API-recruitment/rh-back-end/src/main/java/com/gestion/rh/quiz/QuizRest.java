package com.gestion.rh.quiz;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.rh.employee.Employee;
import com.gestion.rh.employee.EmployeeService;
import com.gestion.rh.models.ApplicationContextProvider;
import com.gestion.rh.simpleToken.SimpleToken;
import com.gestion.rh.simpleToken.SimpleTokenService;

@RestController
@CrossOrigin("*")
@RequestMapping("/quizz")
public class QuizRest {

	@Autowired
	private QuizService service;
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private SimpleTokenService tokenService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseEntity<Void> addQuizz(@RequestBody Map<String, Object> quizz, Authentication authentication){
		Employee emp = empService.getEmployeeByUsername(authentication.getName());
		boolean saved = service.addQuizz(quizz, emp);
		return saved == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/employee/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Quiz>> getEmployeeQuizz(@PathVariable("id") long employeeId){
		List<Quiz> quizz = service.getQuizzByEmployee(employeeId);
		return quizz == null ? new ResponseEntity<List<Quiz>>(quizz, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Quiz>>(quizz, HttpStatus.OK);
	}
	
	@RequestMapping(value="/full/{id}", method=RequestMethod.POST)
	public ResponseEntity<List<Question>> getFullQuizz(@PathVariable("id")long quizzId, @RequestBody String tokenValue){
		List<Question> questions = null;
		SimpleToken token = tokenService.getTokenByValue(tokenValue);
		if(token == null) return new ResponseEntity<List<Question>>(questions, HttpStatus.INTERNAL_SERVER_ERROR);
		
		if(token.isValidToken()) {
			questions = service.getFullQuizz(quizzId);
			return questions == null ? new ResponseEntity<List<Question>>(questions, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Question>>(questions, HttpStatus.OK); 
		}
		return new ResponseEntity<List<Question>>(questions, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Quiz> getQuizz(@PathVariable("id")long quizzId){
		Quiz quizz = service.getQuizz(quizzId);
		return quizz == null ? new ResponseEntity<Quiz>(quizz, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Quiz>(quizz, HttpStatus.OK); 
	}
	
	@RequestMapping(value="candidate/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Quiz>> getCandidateQuizz(@PathVariable("id") long candidateId){
		List<Quiz> quizz = service.getCandidateQuizz(candidateId);
		return quizz == null ? new ResponseEntity<List<Quiz>>(quizz, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Quiz>>(quizz, HttpStatus.OK);
	}
	
	@RequestMapping(value="details/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Question>> getFullQuizz(@PathVariable("id") long quizzId){
		List<Question> questions = service.getFullQuizz(quizzId);
		return questions == null ? new ResponseEntity<List<Question>>(questions, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
	}
}
