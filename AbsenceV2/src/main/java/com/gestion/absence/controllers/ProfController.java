package com.gestion.absence.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.hibernate.ejb.criteria.CriteriaQueryCompiler.RenderedCriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;
import com.gestion.absence.metier.ProfMetier;

@Controller
@RequestMapping("prof")
public class ProfController {
	
	@Autowired
	private ProfMetier metier;
	private List<HashedMap<String, List<Student>>> grps;
	private List<HashedMap<String, List<Presence>>> prs;
	private int seance_id;
	
	@RequestMapping(value="/profil", method=RequestMethod.GET)
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
		return "Prof/profil";
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
//		System.out.println(week + "  " + year + "  "+option);
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
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";
	}
	
	@RequestMapping(value="/faireAbsence", method=RequestMethod.GET)
	public String faireAbsence(HttpSession session, Model model) {
		Professeur prof = (Professeur) session.getAttribute("prof");
		List<Module> modules = metier.getModules(prof.getId());
		model.addAttribute("modules", modules);
		return "Prof/faire_absence";
	}
	
	@RequestMapping(value="/detail_std", method=RequestMethod.GET)
	public String detailStd(@RequestParam("std_id") int id, Model model) {
		Student s = metier.getStudent(id);
		List<Presence> presences = metier.getPresence(id);
		model.addAttribute("student", s);
		model.addAttribute("presences", presences);
		return "Prof/detail_std";
	}
	
	//AJAX Call
	@RequestMapping(value="/loadSeances" ,method=RequestMethod.POST)
	public @ResponseBody String loadSeances(@RequestBody Map<String, String> data){
		System.out.println(Integer.valueOf(data.get("module")));
		List<Seance> seances = metier.getSeances(Integer.valueOf(data.get("module")), 0);
		String result;
		result = "[";
		for(Seance s : seances) {
			result = result + "{";
				result = result + " \"id\" :  \""+s.getId()+"\", ";
				result = result + " \"date\" :  \""+String.valueOf(s.getDate()).substring(0, 10)+"\", ";
				result = result + " \"heure\" :  \""+s.getHeure()+"\" ";
			result = result + "}";
			if(seances.indexOf(s) != seances.size() - 1) result = result + ",";
		}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	//AJAX Call
	@RequestMapping(value="/loadSeancesV1" ,method=RequestMethod.POST)
	public @ResponseBody String loadSeancesV1(@RequestBody Map<String, String> data){
		System.out.println(Integer.valueOf(data.get("module")));
		List<Seance> seances = metier.getSeances(Integer.valueOf(data.get("module")), 1);
		String result;
		result = "[";
		for(Seance s : seances) {
			result = result + "{";
				result = result + " \"id\" :  \""+s.getId()+"\", ";
				result = result + " \"date\" :  \""+String.valueOf(s.getDate()).substring(0, 10)+"\", ";
				result = result + " \"heure\" :  \""+s.getHeure()+"\" ";
			result = result + "}";
			if(seances.indexOf(s) != seances.size() - 1) result = result + ",";
		}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="/absence", method=RequestMethod.POST)
	public String absence(@RequestParam("seance") int seance_id, Model model) {
		this.seance_id = seance_id;
		grps = metier.getGroupes(seance_id);
		List<Student> stds;
		for(HashedMap<String, List<Student>> groupe : grps) {
			System.out.println("-----------------------------------------------------");
			for(Map.Entry<String, List<Student>> m : groupe.entrySet()) {
				System.out.println("Groupe name is: "+m.getKey());
				stds = m.getValue();
				for(Student s: stds) System.out.println(s.getName()+ " "+ s.getLastName());
			}
		}
		model.addAttribute("groupes", grps);
		return "Prof/absence"; // tmp
	}
	
	@RequestMapping(value="/absence", method=RequestMethod.GET)
	public String absenceGet() {
		return "Prof/faire_absence";
	}
	
	@RequestMapping(value="/saveAbs", method=RequestMethod.POST)
	public String saveAbsence(@RequestParam Map<String,String> params) {
		metier.savePresence(grps, params, seance_id);
		return "redirect:/prof/faireAbsence";
	}
	
	@RequestMapping(value="/mdfAbsence", method=RequestMethod.GET)
	public String mdfAbsence(HttpSession session, Model model) {
		Professeur prof = (Professeur) session.getAttribute("prof");
		List<Module> modules = metier.getModules(prof.getId());
		model.addAttribute("modules", modules);
		return "Prof/mdf_absence";
	}
	
	@RequestMapping(value="/getabsence", method=RequestMethod.POST)
	public String getPresence(@RequestParam("seance") int seance_id, Model model) {
		this.prs = metier.getPresences(seance_id);
		this.seance_id = seance_id;
		List<Presence> presence;
		for(HashedMap<String, List<Presence>> groupe : prs) {
			System.out.println("-----------------------------------------------------");
			for(Map.Entry<String, List<Presence>> m : groupe.entrySet()) {
				System.out.println("Groupe name is: "+m.getKey());
				presence = m.getValue();
				for(Presence pr: presence) System.out.println(pr.getStudent().getName()+ " "+ pr.getStudent().getLastName()+" ispresent:  "+pr.isPresent()+ " isjustified: "+pr.isJustified());
			}
		}
		model.addAttribute("presences", this.prs);
		return "Prof/mdf_absence_final";
	}
	
	@RequestMapping(value="/getabsence", method=RequestMethod.GET)
	public String getPresenceB_mdf(@RequestParam("seance") int seance_id, Model model) {
		this.prs = metier.getPresences(seance_id);
		this.seance_id = seance_id;
		List<Presence> presence;
		for(HashedMap<String, List<Presence>> groupe : prs) {
			System.out.println("-----------------------------------------------------");
			for(Map.Entry<String, List<Presence>> m : groupe.entrySet()) {
				System.out.println("Groupe name is: "+m.getKey());
				presence = m.getValue();
				for(Presence pr: presence) System.out.println(pr.getStudent().getName()+ " "+ pr.getStudent().getLastName()+" ispresent:  "+pr.isPresent()+ " isjustified: "+pr.isJustified());
			}
		}
		model.addAttribute("presences", this.prs);
		return "Prof/mdf_absence_final";
	}
	
	@RequestMapping(value="/mdfAbsence", method=RequestMethod.POST)
	public String mdfAbsence(@RequestParam Map<String,String> params) {
		String result = metier.updatePresence(prs, params);
		System.out.println(result);
		return "redirect:/prof/getabsence?seance="+seance_id;
	}
	
	@RequestMapping(value="/gestion_modules", method=RequestMethod.GET)
	public String getModules(HttpSession session, Model model) {
		Professeur prof = (Professeur) session.getAttribute("prof");
		List<Module> modules = metier.getModules(prof.getId());
		model.addAttribute("modules", modules);
		return "Prof/modules";
	}
	
	@RequestMapping(value="/gestion_groupes", method=RequestMethod.GET)
	public String getGroupes(HttpSession session, Model model) {
		Professeur prof = (Professeur) session.getAttribute("prof");
		List<Groupe> groupes = metier.getGroupes_prof(prof.getId());
		model.addAttribute("groupes", groupes);
		return "Prof/groupes";
	}
}
