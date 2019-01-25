package com.gestion.rh.employee;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

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
import org.springframework.web.bind.annotation.RestController;

import com.gestion.rh.models.ApplicationContextProvider;

@RestController
@CrossOrigin("*")
@RequestMapping("/rh")
public class EmployeeRest {

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	@Value("${dir.photos.rhs}")
	private String photoDir;
	
	@RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee emp){
		emp = empService.saveEmployee(emp);
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id){
		Employee emp = empService.getEmployee(id);
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/connectedUser", method=RequestMethod.GET)
	public ResponseEntity<Employee> getConnectedEmployee(Authentication authentication){
		Employee emp = empService.getEmployeeByUsername(authentication.getName());
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/photo/{name}", method=RequestMethod.GET)
	public ResponseEntity<Void> downloadPhoto(HttpServletResponse response, @PathVariable("name") String name){
		try {
			System.out.println("photo name is: "+name);
			
			String filepath = this.photoDir+name;
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
}
