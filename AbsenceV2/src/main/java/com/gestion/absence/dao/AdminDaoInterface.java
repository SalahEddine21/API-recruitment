package com.gestion.absence.dao;

import java.util.Date;
import java.util.List;

import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Seances_grps;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

public interface AdminDaoInterface {
	
	public List<Object> getEmp(String semaine, String year, String option);
	public List<String> getFiliers(String year);
	public Seance saveSeance(Seance s);
	public Semaine saveSemaine(Semaine sm);
	public Module getModule(String titre);
	public Groupe getGroupe(String name, String year, String option);
	public Semaine getSemaine(Date date_debut, Date date_fin);
	public void saveSeanceTogrp(Seances_grps sgrp);
	public List<Semaine> getSemaine();
	public Groupe addGroups(Groupe g);
	public void saveStudent(Student s);
	public List<Groupe> getGroupes();
	public List<Student> getStudents(int groupe);
	public void saveProf(Professeur p);
	public List<Professeur> getDataProfs(int start);
	public List<Module> getModuleProf(int id_prof);
	public long CountProfs();
	public Professeur getProf(String name, String lastname)throws Exception;
	public void saveModule(Module m);
	public List<Module> getModules(String semestre);
	public List<Presence> getPresence(int std_id);
	public Student getStudent(int std_id);
}
