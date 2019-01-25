package com.gestion.absence.dao;


import java.util.List;

import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

public interface StudentDaoInterface {

	public Student getStudent(String code, String password);
	public List<Presence> getPresences(int student_id);
	public List<Object> getEmp(String semaine, String year, String option);
	public List<Semaine> getSemaine();


}
