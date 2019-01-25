package com.gestion.absence.controllers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;
import com.gestion.absence.metier.AdminMetier;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminMetier metier;
		
	@RequestMapping(value="/profil")
	public String profil(Model model) {
		List<Emploi> emplois = metier.getEmploi("semaine1", "1A", null);
		List<Semaine> semaines = metier.getSemaines();
		List<String> times = new ArrayList<String>();
		times.add("8h-10h");
		times.add("10h-12h");
		times.add("14h-16h");
		times.add("16h-18h");
		
		model.addAttribute("emplois", emplois);
		model.addAttribute("semaines", semaines);
		model.addAttribute("times", times);
		return "Admin/profil";
	}
	
	@RequestMapping(value="/profil", method=RequestMethod.POST)
	public String LoadEmploi(@RequestParam("week") String week, @RequestParam("year") String year, @RequestParam(value="option" ,required=false ) String option, Model model) {
		List<Emploi> emplois = metier.getEmploi(week, year, option);
		List<Semaine> semaines = metier.getSemaines();
		List<String> times = new ArrayList<String>();
		times.add("8h-10h");
		times.add("10h-12h");
		times.add("14h-16h");
		times.add("16h-18h");
		model.addAttribute("emplois", emplois);
		model.addAttribute("semaines", semaines);
		model.addAttribute("times", times);
//		System.out.println(week + " " + year + "  "+option);
		return "Admin/profil";
	}
	
	//AJAX Call
	@RequestMapping(value="LoadFiliers" ,method=RequestMethod.POST)
	public @ResponseBody String loadGrps(@RequestBody Map<String, String> data){
		List<String> filiers = metier.getFiliers(data.get("year"));
		String result;
		result = "[";
		for(String filiere : filiers) {
			result = result + "{" + " \"name\" :  \""+filiere+"\" }";
			if(filiers.indexOf(filiere) != filiers.size() - 1) result = result + ",";
		}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="gestion_seances", method=RequestMethod.GET)
	public String GestionSeances() {
		return  "Admin/gestion_seances";
	}
	
	@RequestMapping(value="/gestion_seances", method=RequestMethod.POST)
	public String ImportEmploi(@RequestParam("year") String year, @RequestParam("emploi") MultipartFile file, @RequestParam("date_debut") String date_debut, @RequestParam("date_fin") String date_fin, @RequestParam("semaine") String semaine, @RequestParam(value="option", required=false) String option, HttpSession session, Model model) {
		String path=session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();
		
		System.out.println(path+ " " +filename);
		
		try {
	        byte barr[]=file.getBytes();  
	        
	        BufferedOutputStream bout=new BufferedOutputStream(  
	                 new FileOutputStream(path+"/"+filename));  
	        bout.write(barr);  
	        bout.flush();  
	        bout.close(); 
	        System.out.println("admincontroller");
	        String result = metier.saveEmploi(path+"/"+filename, year, option, date_debut, date_fin, semaine);
	        System.out.println(result);
	        model.addAttribute("result", result);
		}catch(Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		
		return "Admin/gestion_seances";
	}
	
	@RequestMapping(value="/gestion_groupes", method=RequestMethod.GET)
	public String addGroupe() {
		return "Admin/gestion_groupes";
	}
	
	@RequestMapping(value="/gestion_groupes", method=RequestMethod.POST)
	public String addGroupe(@RequestParam(value="filiere", required=false) String filiere, @RequestParam("name") String name, @RequestParam("scolaire") String scolaire, @RequestParam("annee") String annee, @RequestParam("etudiants") MultipartFile file, HttpSession session, Model model ) {
		String path=session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();
		
		System.out.println(path+ " " +filename);
		
		try {
	        byte barr[]=file.getBytes();  
	        
	        BufferedOutputStream bout=new BufferedOutputStream(  
	                 new FileOutputStream(path+"/"+filename));  
	        bout.write(barr);  
	        bout.flush();  
	        bout.close(); 
	        String result = metier.saveGroupe(filiere, annee, scolaire, name, path+"/"+filename);
	        //System.out.println(result);
	        model.addAttribute("result", result);
		}catch(Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return "Admin/gestion_groupes";
	}
	
	@RequestMapping(value="/detail_groupes", method=RequestMethod.GET)
	public String detailGrp(Model model) {
		List<Groupe> grps = metier.getGroupes();
		model.addAttribute("grps", grps);
		return "Admin/detail_groupes";
	}
	
	@RequestMapping(value="/LoadStds", method=RequestMethod.POST)
	public @ResponseBody String LoadStds(@RequestBody Map<String, String> data) { // AJAX CALL
		String result = null;
		int groupe = Integer.valueOf(data.get("groupe"));
		List<Student> students = metier.getStudents(groupe);
		
		result = "[";
		for(Student s: students) {
			result = result + "{";
				result = result + " \"id\" :  \""+s.getId()+"\", ";
				result = result + " \"code\" :  \""+s.getCode()+"\", ";
				result = result + " \"name\" :  \""+s.getName()+"\", ";
				result = result + " \"lastname\" :  \""+s.getLastName()+"\", ";
				result = result + " \"email\" :  \""+s.getEmail()+"\" ";
			result = result + "}";
			if( students.indexOf(s) != students.size() -1 ) result = result + ",";
		}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="/detail_std", method=RequestMethod.GET)
	public String detailStd(@RequestParam("std_id") int id, Model model) {
		Student s = metier.getStudent(id);
		List<Presence> presences = metier.getPresence(id);
		model.addAttribute("student", s);
		model.addAttribute("presences", presences);
		return "Admin/detail_std";
	}
	
	@RequestMapping(value="/gestion_profs", method=RequestMethod.GET)
	public String GestionPorfs() {
		return "Admin/gestion_profs";
	}
	
	@RequestMapping(value="/gestion_profs", method=RequestMethod.POST)
	public String LoadProfs(@RequestParam("profs") MultipartFile file, HttpSession session, Model model ) {
		String path=session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();
		
		System.out.println(path+ " " +filename);
		
		try {
	        byte barr[]=file.getBytes();  
	        
	        BufferedOutputStream bout=new BufferedOutputStream(  
	                 new FileOutputStream(path+"/"+filename));  
	        bout.write(barr);  
	        bout.flush();  
	        bout.close(); 
	        String result = metier.LoadProfs(path+"/"+filename);
	        System.out.println(result);
	        model.addAttribute("result", result);
		}catch(Exception e) {
			model.addAttribute("result", e.getMessage());
		}	
		return "Admin/gestion_profs";
	}
	
	@RequestMapping(value="/detail_profs", method=RequestMethod.GET)
	public String DetailProfs(Model model) {
		long number = metier.countProfs();
		model.addAttribute("limit", number);
		return "Admin/detail_profs";
	}
	
	@RequestMapping(value="/LoadProfs", method=RequestMethod.POST)
	public @ResponseBody String getDataProf( @RequestBody Map<String, String> data ) { // AJAX CALL
		int startID = Integer.valueOf(data.get("startID"));
		List<Professeur> profs = metier.getDataProfs(startID);
		String result=null;
		result = "[";
			for(Professeur prof : profs) {
				result = result + "{";
					result = result + " \"nom\" :  \""+prof.getName()+"\", ";
					result = result + " \"prenom\" :  \""+prof.getLastName()+"\", ";
					result = result + " \"email\" :  \""+prof.getEmail()+"\", ";
					result = result + " \"modules\" :  ";
						result = result + "[";
							for(Module m : prof.getModules()) {
								result = result + "{";
									result = result + " \"titre\" :  \""+m.getTitre()+"\" ";
								result = result + "}";
								if(prof.getModules().indexOf(m) != prof.getModules().size()-1) result = result + ",";
							}
						result = result + "]";
				result = result + "}";
				if(profs.indexOf(prof) != profs.size()-1 ) result = result + ",";
			}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="/gestion_modules", method=RequestMethod.GET)
	public String gestionModule() {
		return "Admin/gestion_modules";
	}
	
	@RequestMapping(value="/gestion_modules", method=RequestMethod.POST)
	public String loadModules(@RequestParam("modules") MultipartFile file, HttpSession session, Model model ) {
		String path=session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();
		
		System.out.println(path+ " " +filename);
		
		try {
	        byte barr[]=file.getBytes();  
	        
	        BufferedOutputStream bout=new BufferedOutputStream(  
	                 new FileOutputStream(path+"/"+filename));  
	        bout.write(barr);  
	        bout.flush();  
	        bout.close(); 
	        String result = metier.loadModules(path+"/"+filename);
	        model.addAttribute("result", result);
		}catch(Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "Admin/gestion_modules";
	}
	
	@RequestMapping(value="/detail_modules", method=RequestMethod.GET)
	public String detailModules() {
		return "Admin/detail_modules";
	}
	
	//AJAX CALL
	@RequestMapping(value="/loadModules", method=RequestMethod.POST)
	public @ResponseBody String getModules( @RequestBody Map<String, String> data ) { // AJAX CALL
		List<Module> modules = metier.getModule(data.get("semestre"));
		String result=null;
		result = "[";
			for(Module m: modules) {
				result = result + "{";
				result = result + " \"titre\" :  \""+m.getTitre()+"\", ";
				result = result + " \"enseignant\" :  \""+m.getProfesseur().getName()+" "+m.getProfesseur().getLastName() +"\" ";
				result = result + "}";
				if(modules.indexOf(m) != modules.size()-1 ) result = result + ",";
			}
		result = result + "]";
		System.out.println(result);
		return result;
	}	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logOut() {
		return "redirect:/home/login";
	}
}
