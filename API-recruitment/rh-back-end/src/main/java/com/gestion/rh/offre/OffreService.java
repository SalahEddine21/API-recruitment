package com.gestion.rh.offre;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gestion.rh.candidate.cv.Cv;
import com.gestion.rh.candidate.cv.CvService;
import com.gestion.rh.mailing.EmailService;
import com.gestion.rh.models.ApplicationContextProvider;

@Service
public class OffreService {

	@Autowired
	private OffreRepository offreRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CvService cvService;
	
	@Autowired
	private ApplicationContextProvider provider;
	
	@Value("${dir.candidates.cvs}")
	private String cvDir;
	
	public Offre saveOffre(Offre offre) {
		try {
			offreRepo.save(offre);
			return offre;
		} catch (Exception e) {
			System.out.println("Exception: "+ e.getMessage());
			return null;
		}
	}
	
	public List<Offre> getOffresByEmployee(long employeeId){
		try {
			return offreRepo.getOffresByEmployee(employeeId);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Offre getOffreByID(long offreId) {
		try {
			return offreRepo.getOffreByID(offreId);
		} catch (Exception e) {
			System.out.println("Exception: "+ e.getMessage());
			return null;
		}
	}
	
	public boolean deleteOffre(long offreId) {
		try {
			offreRepo.deleteById(offreId);
			return true;
		} catch (Exception e) {
			System.out.println("Exception: "+ e.getMessage());
			return false;
		}
	}
	
	public List<Offre> getallOffres(){
		try {
			return offreRepo.getallOffres();
		} catch (Exception e) {
			System.out.println("Exception: "+ e.getMessage());
			return null;
		}
	}
	
	public List<Offre> searchOffres(Map<String, String> data){
		try {
			String city = (String) data.get("city");
			String type = (String) data.get("type");
			String domaine = (String) data.get("domaine");
			
			if(city != null) System.out.println("city is: "+city);
			if(type != null) System.out.println("type is: "+type);
			if(domaine != null) System.out.println("domaine is: "+domaine);
			
			if(city != null) {
				if(type != null && domaine != null) return offreRepo.searchByDomainTypeCity(domaine, type, city);
				else if(type != null) return offreRepo.searchByTypeCity(type, city);
				else if(domaine != null) return offreRepo.searchByDomainCity(domaine, city);
				else return offreRepo.searchByCity(city);
			}
			
			if(type != null) {
				if(domaine != null) return offreRepo.searchByTypeDomain(type, domaine);
				return offreRepo.searchByType(type);
			}
			
			return offreRepo.searchByDomain(domaine);	
		} catch (Exception e) {
			System.out.println("Exception: "+ e.getMessage());
			return null;
		}
	}
	
	public boolean applyOffre(Map<String, String> data) {
		String subject = (String) data.get("subject");
		String to = (String) data.get("to");
		String from = (String) data.get("from");
		String body = (String) data.get("body");
		long cvId = Long.valueOf((String) data.get("cvid"));
		
		System.out.println("subject: "+subject);
		System.out.println("to: "+to);
		System.out.println("from: "+from);
		System.out.println("body: "+body);
		System.out.println("cv id is: "+cvId);
		
		try {
			Cv cv = cvService.getCvById(cvId);
			String cvpath = this.cvDir+cv.getName();
			File file = (File) provider.getApplicationContext().getBean("file", cvpath);
			emailService.applyJob(subject, from, to, body, file);
			return true;
		} catch (Exception e) {
			System.out.println("Exception: "+ e.getMessage());
			return false;
		}
	}
}
