package com.gestion.rh.offre;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.gestion.rh.candidature.Candidature;
import com.gestion.rh.employee.Employee;
import com.gestion.rh.employee.EmployeeService;
import com.gestion.rh.models.ApplicationContextProvider;

@RestController
@CrossOrigin("*")
@RequestMapping("/offre")
public class OffreRest {
	
	@Autowired
	private ApplicationContextProvider provider;	
	
	@Autowired
	private OffreService offreService;
	
	@Autowired
	private EmployeeService empService;
	
	@Value("${dir.types}")
	private String typesFile;
	
	@Value("${dir.domains}")
	private String domainesFile;
	
	@Value("${dir.photos.offres}")
	private String pathPhotos;

	@RequestMapping(value="/types", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getTypes(){
		
		List<String> types = null;
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = (FileReader) provider.getApplicationContext().getBean("fileReader", typesFile);
			br = (BufferedReader) provider.getApplicationContext().getBean("bufferedReader", fr);
			
			String type;
			types = (List<String>) provider.getApplicationContext().getBean("getStringList");
			
			while( (type = br.readLine()) != null ) types.add(type);
			
			fr.close();
			br.close();
			
			return new ResponseEntity<List<String>>(types, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("EXCEPTION: "+e.getMessage());
			return new ResponseEntity<List<String>>(types, HttpStatus.NO_CONTENT);	
		}
	}
	
	@RequestMapping(value="/domaines", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getDomaines(){
		List<String> domaines = null;
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = (FileReader) provider.getApplicationContext().getBean("fileReader", domainesFile);
			br = (BufferedReader) provider.getApplicationContext().getBean("bufferedReader", fr);
			
			String domaine;
			domaines = (List<String>) provider.getApplicationContext().getBean("getStringList");
			
			while( (domaine = br.readLine()) != null ) domaines.add(domaine);
			
			fr.close();
			br.close();
			
			return new ResponseEntity<List<String>>(domaines, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("EXCEPTION: "+e.getMessage());
			return new ResponseEntity<List<String>>(domaines, HttpStatus.NO_CONTENT);	
		}	
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<Void> saveOffre(@RequestPart("offre") Offre offre, @RequestPart("file") MultipartFile file, HttpServletRequest request, Authentication authentication){		
		if(!file.isEmpty()) {
			offre.setPhoto(file.getOriginalFilename());
			try {
				file.transferTo((File) provider.getApplicationContext().getBean("file", this.pathPhotos+offre.getPhoto()));
			} catch (Exception e) {
				System.out.println("Exception: "+ e.getMessage());
				offre.setPhoto(null);
			}
		}
		Employee emp = empService.getEmployeeByUsername(authentication.getName());
		offre.setEmployee(emp);
		
		Date date = (Date) provider.getApplicationContext().getBean("utilDate");
		java.sql.Date sqldate = (java.sql.Date) provider.getApplicationContext().getBean("sqlDate", date.getTime());
		//SimpleDateFormat format = (SimpleDateFormat) provider.getApplicationContext().getBean("simpleDateformat", "yyyy-MM-dd");
		offre.setDate(sqldate);
		
		return offreService.saveOffre(offre) == null ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/employee/all", method=RequestMethod.GET)
	public ResponseEntity<List<Offre>> getOffresByEmployee(Authentication authentication){
		System.out.println("hello there !");
		Employee emp = empService.getEmployeeByUsername(authentication.getName());
		List<Offre> offres = offreService.getOffresByEmployee(emp.getId());
		return offres == null ? new ResponseEntity<List<Offre>>(offres, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Offre>>(offres, HttpStatus.OK);
	}
	
	@RequestMapping(value="/image/{name}", method=RequestMethod.GET)
	public ResponseEntity<Void> getImage(HttpServletResponse response, @PathVariable("name") String filename){
		try {
			System.out.println("photo name is: "+filename);
			
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Offre> getOffre(@PathVariable("id") long offreID){
		Offre o = offreService.getOffreByID(offreID);
		return o == null ? new ResponseEntity<Offre>(o, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Offre>(o, HttpStatus.OK);
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.PUT)
	public ResponseEntity<Void> editOffre(@RequestBody Offre offre){
		Offre o = offreService.getOffreByID(offre.getId());
		if(o != null) {
			o.setTitle(offre.getTitle());
			o.setDescription(offre.getDescription());
			o.setType(offre.getType());
			o.setDomaine(offre.getDomaine());
			o = offreService.saveOffre(o);
		}
		return o == null ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOffre(@PathVariable("id") long offreId){
		boolean deleted = offreService.deleteOffre(offreId);
		return deleted == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseEntity<List<Offre>> getallOffres(){
		System.out.println("get all offres !");
		List<Offre> offres = offreService.getallOffres();
		return offres == null ? new ResponseEntity<List<Offre>>(offres, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Offre>>(offres, HttpStatus.OK);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ResponseEntity<List<Offre>> searchOffres(@RequestBody Map<String, String> data){
		List<Offre> offres = offreService.searchOffres(data);
		return offres == null ? new ResponseEntity<List<Offre>>(offres, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<Offre>>(offres, HttpStatus.OK);
	}
	
	@RequestMapping(value="/apply", method=RequestMethod.POST)
	public ResponseEntity<Void> offreApply(@RequestBody Map<String, String> data){
		boolean sent = offreService.applyOffre(data);
		return sent == false ? new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
