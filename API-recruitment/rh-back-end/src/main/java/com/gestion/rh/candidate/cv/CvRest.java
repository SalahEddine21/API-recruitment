package com.gestion.rh.candidate.cv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestion.rh.candidate.Candidate;
import com.gestion.rh.candidate.CandidateService;
import com.gestion.rh.models.ApplicationContextProvider;

@RestController
@CrossOrigin("*")
@RequestMapping("/cv")
public class CvRest {
 
	@Autowired
	private CvService service;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	@Value("${dir.candidates.cvs}")
	private String cvDir;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Void> getCvCandidate(HttpServletResponse response, @PathVariable("id") long cvID){
		try {
			System.out.println("id cv is: "+cvID);
			Cv cv = service.getCvCandidate(cvID);
			System.out.println("cv's name is: "+cv.getName());
			
			String filepath = this.cvDir+cv.getName();
			System.out.println("cv path file is: "+filepath);
			
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
	
	@RequestMapping(value="candidate/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Cv>> getCvs(@PathVariable("id") long candidateId){
		List<Cv> cvs = service.getCvsBYcandidate(candidateId);
		return cvs == null ? new ResponseEntity<List<Cv>>(cvs, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Cv>>(cvs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCv(@PathVariable("id") long cvId){
		Cv cv = service.getCvById(cvId);
		if(cv == null) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		String filepath = this.cvDir+cv.getName();
		File file = (File) provider.getApplicationContext().getBean("file", filepath);
		if(file.delete()) {
			boolean deleted = service.deleteCvById(cv.getId());
			return deleted == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="upload/{candidateId}", method=RequestMethod.POST)
	public ResponseEntity<Cv> uploadCv(@PathVariable("candidateId") long candidateId, @RequestPart("file") MultipartFile file){
		Cv cv = (Cv) provider.getApplicationContext().getBean("cv", Cv.class);
		if(!file.isEmpty()) {
			Candidate c = candidateService.getCandidateById(candidateId);
			cv.setCandidate(c);
			cv.setName(file.getOriginalFilename());
			try {
				file.transferTo((File) provider.getApplicationContext().getBean("file", this.cvDir+cv.getName()));
				cv = service.saveCv(cv);
				return new ResponseEntity<Cv>(cv, HttpStatus.OK);
			} catch (Exception e) {
				System.out.println("Exception: "+ e.getMessage());
				return new ResponseEntity<Cv>(cv, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<Cv>(cv, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="visible/{candidateId}", method=RequestMethod.GET)
	public ResponseEntity<Cv> getVisibleCv(@PathVariable("candidateId") long candidateId){
		Cv cv = service.getVisibleCv(candidateId);
		return cv == null ? new ResponseEntity<Cv>(cv, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Cv>(cv, HttpStatus.OK);
	}
	
}
