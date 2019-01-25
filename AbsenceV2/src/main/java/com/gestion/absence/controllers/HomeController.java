package com.gestion.absence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Student;
import com.gestion.absence.metier.ProfMetier;
import com.gestion.absence.metier.StudentMetier;

@Controller
@RequestMapping("home")
@SessionAttributes(value= {"prof", "student"})
public class HomeController {

	@Autowired
	private ProfMetier profmetier;
	@Autowired
	private StudentMetier stdmetier;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return "home";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String logIn() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String logIn(@RequestParam("code") String code, @RequestParam("password") String password, Model model) {
			if(code.equals("test") && password.equals("test")) return "redirect:/admin/profil";
			Student std = stdmetier.getStudent(code, password);
			if(std != null) {
				model.addAttribute("student", std);
				return "redirect:/student/profil";
			
			}else {
				Professeur prof = profmetier.getProf(code, password);
				if(prof != null) {
					model.addAttribute("prof", prof);
					return "redirect:/prof/profil";
				}
			}
			model.addAttribute("unkown", "Unkown user !!");
		return "login";
	}
}
