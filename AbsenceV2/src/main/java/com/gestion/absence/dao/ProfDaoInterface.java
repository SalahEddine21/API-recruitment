package com.gestion.absence.dao;

import java.util.Date;
import java.util.List;

import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

public interface ProfDaoInterface {

	public Professeur getProf(String code, String password);
	public List<Object> getEmp(String semaine, String year, String option);
	public List<String> getFiliers(String year);
	public List<Semaine> getSemaine();
	public List<Module> getModules(int prof_id);
	public List<Seance> getSeances(int module_id, int old);
	public List<Student> getStudents(int seance_id);
	public Seance getSeance(int seance_id);
	public void savePresence(Presence p);
	public Student getStudent(int student_id);
	public List<Presence> getPresences(int seance_id); 
	public void updatePresence(Presence p);
	public List<Groupe> getGroupes(int prof_id);
	public List<Presence> getPresence(int std_id);
}
