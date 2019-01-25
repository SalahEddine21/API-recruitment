package com.gestion.rh.candidate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.rh.mailing.EmailService;
import com.gestion.rh.models.ApplicationContextProvider;
import com.gestion.rh.quiz.Quiz;
import com.gestion.rh.quiz.QuizService;
import com.gestion.rh.simpleToken.SimpleToken;
import com.gestion.rh.simpleToken.SimpleTokenService;

@RestController
@CrossOrigin("*")
@RequestMapping("/candidate")
public class CandidateRest {
	
	@Autowired
	private CandidateService service;
	
	@Autowired
	private QuizService quizzService;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	@Autowired
	private SimpleTokenService tokenService;
	
	@Value("${dir.photos.candidates}")
	private String pathPhotos;
	
	@Value("${test.token.expTime}")
	private int experationTime;

	@RequestMapping(value="/photo/{name}", method=RequestMethod.GET)
	public ResponseEntity<Void> getPhoto(HttpServletResponse response, @PathVariable("name") String filename){
		try {
			System.out.println("photo candidate's name: "+filename);
			
			String filepath = this.pathPhotos+filename;
			System.out.println("path file is: "+filepath);
			
			File file = (File) provider.getApplicationContext().getBean("file", filepath);
			
			if(!file.exists()) {
	            String errorMessage = "Sorry. The file you are looking for does not exist";
	            System.out.println(errorMessage);
	         
	            OutputStream outputStream = response.getOutputStream();
	            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
	            outputStream.close();
	            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND) ;
			}
			
	        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
	        if(mimeType==null){
	            System.out.println("mimetype is not detectable, default was taked");
	            mimeType = "application/octet-stream";
	        }			
	        
	        response.setContentType(mimeType);

	        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
	 
	        response.setContentLength((int)file.length());
	 
	        FileInputStream fis = (FileInputStream) provider.getApplicationContext().getBean("fileInputStream", file);
	        InputStream inputStream = (BufferedInputStream) provider.getApplicationContext().getBean("bufferedInputStream", fis);
	        
	        FileCopyUtils.copy(inputStream, response.getOutputStream());
			
	        return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT) ;
		}	
	}
	
	@RequestMapping(value="/convocate", method=RequestMethod.POST)
	public ResponseEntity<Void> convocate(@RequestBody Map<String, String> data){
		boolean sent = service.convocateCandidate(data.get("subject"), data.get("to"), data.get("body"));
		return  sent == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/connectedUser", method=RequestMethod.GET)
	public ResponseEntity<Candidate> getCurrentCandidate(Authentication authentication){
		Candidate c = service.getCandidateByUsername(authentication.getName());
		return c == null ? new ResponseEntity<Candidate>(c , HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Candidate>(c , HttpStatus.OK);
	}
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseEntity<List<Candidate>> getAllCandidates(){
		List<Candidate> candidates = service.getAllCandidates();
		return candidates == null ? new ResponseEntity<List<Candidate>>(candidates , HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Candidate>>(candidates , HttpStatus.OK);
	}
	
	@RequestMapping(value="/mailing", method=RequestMethod.POST)
	public ResponseEntity<Void> mailCandidate(@RequestBody Map<String, String> data){
		boolean sent = service.mailCandidate(data);
		return  sent == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/convocate/quizz/{id}", method=RequestMethod.POST)
	public ResponseEntity<Void> convocateQuizz(@PathVariable("id") long quizzId, @RequestBody Candidate candidate){
		SimpleToken token = (SimpleToken) provider.getApplicationContext().getBean("simpleToken", UUID.randomUUID().toString(),LocalDate.now(), experationTime);
		token.setCandidate(candidate);
		token = tokenService.saveToken(token);
		if(token == null) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		Quiz quizz = quizzService.getQuizz(quizzId);
		boolean convocated = service.convocateQuizz(quizzId, candidate, token.getTokenValue(), quizz);
		return  convocated == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
