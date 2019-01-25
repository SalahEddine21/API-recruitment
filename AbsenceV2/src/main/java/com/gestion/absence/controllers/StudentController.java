package com.gestion.absence.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;
import com.gestion.absence.metier.StudentMetier;

@Controller
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	private StudentMetier metier;
	
	@RequestMapping(value="/profil", method=RequestMethod.GET)
	public String profil(Model model) {
		return "Student/profil";
	}
	
	@RequestMapping(value="/absence", method=RequestMethod.GET)
	public String absence(HttpSession session, Model model) {
		Student std = (Student) session.getAttribute("student");
		List<Presence> presences = metier.getPresence(std.getId());
		model.addAttribute("presences", presences);
		for(Presence p: presences) System.out.println(p.getStudent().getCode() + " " + p.isPresent());
		return "Student/absence";
	}
	
	@RequestMapping(value="/emploi", method=RequestMethod.GET)
	public String emploi(HttpSession session, Model model) {
		Student std = (Student) session.getAttribute("student");
		List<Emploi> emplois = null;
		if(std.getGroupe().getAnnee().equals("1A")) emplois = metier.getEmploi("semaine1", "1A", null);
		else emplois = metier.getEmploi("semaine1", std.getGroupe().getAnnee(), std.getGroupe().getFiliere());
		
		List<Semaine> semaines = metier.getSemaines();
		List<String> times = new ArrayList<String>();
		times.add("8h-10h");
		times.add("10h-12h");
		times.add("14h-16h");
		times.add("16h-18h");
		
		model.addAttribute("emplois", emplois);
		model.addAttribute("semaines", semaines);
		model.addAttribute("times", times);
		
		return "Student/emploi";
	}
	
	@RequestMapping(value="/emploi", method=RequestMethod.POST)
	public String LoadEmploi(@RequestParam("week") String week, Model model, HttpSession session) {
		
		Student std = (Student) session.getAttribute("student");
		List<Emploi> emplois = null;
		if(std.getGroupe().getAnnee().equals("1A")) emplois = metier.getEmploi(week, "1A", null);
		else emplois = metier.getEmploi(week, std.getGroupe().getAnnee(), std.getGroupe().getFiliere());
		
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
		return  "Student/emploi";
	}
		
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";
	}
}
