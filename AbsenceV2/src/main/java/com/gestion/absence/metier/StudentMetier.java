package com.gestion.absence.metier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.gestion.absence.Beans.ApplicationContextProvider;
import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.dao.StudentDaoInterface;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

@Transactional
public class StudentMetier {

	private StudentDaoInterface dao;
	
	@Autowired
	private ApplicationContextProvider provider;
		
	public StudentDaoInterface getDao() {
		return dao;
	}

	public void setDao(StudentDaoInterface dao) {
		this.dao = dao;
	}

	public Student getStudent(String code, String password) {
		return dao.getStudent(code, password);
	}
	
	public List<Presence> getPresence(int student){
		List<Presence> prs = dao.getPresences(student);
		return prs;
	}
	
	public List<Emploi> getEmploi(String semaine, String year, String option){
		List<Object> objects = dao.getEmp(semaine, year, option);
		if(objects == null) return null;
		
		List<Emploi> emplois = new ArrayList<Emploi>();
		
		Emploi emp;
		int indice;
		String heure;
		List<String> groupes;
		SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd"); // hase to be swaped to the xml spring file
		DateFormat day_format=new SimpleDateFormat("EEEE"); // hase to be swaped to the xml spring file
		String day;
		Date date;
		
		Iterator itr = objects.iterator();
		while(itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			try {
				date = ndf.parse(String.valueOf(obj[0]).substring(0, 10));
				day = day_format.format(date);
				indice = getIndice(day+" "+String.valueOf(obj[0]).substring(0, 10), emplois);
				
				if(indice != -1) { // date founded in the list
					emp = emplois.get(indice);
					heure = String.valueOf(obj[1]); // get seance's houre
					
					
					groupes = (List<String>) emp.getHashmap_groupes().get(heure); // get the groupe's list of the above seance
					
					if(groupes == null) {
						groupes = new ArrayList<String>();
						groupes.add(String.valueOf(obj[4])); // add the new groupe in the new list
						emp.getHashmap_module().put(heure, String.valueOf(obj[2]));
						emp.getHashmap_prof().put(heure, String.valueOf(obj[3]));
						emp.getHashmap_groupes().put(heure, groupes); // put the groupe in that houre
						
					}else groupes.add(String.valueOf(obj[4])); // adding the groupe to the list geted
						
				}
				else {
					emp = (Emploi) provider.getApplicationContext().getBean("emploi", Emploi.class);
					emp.setDate(day+" "+String.valueOf(obj[0]).substring(0, 10));
					List<String> grps = new ArrayList<String>();
					grps.add(String.valueOf(obj[4]));
						
					emp.getHashmap_groupes().put(String.valueOf(obj[1]), grps); // add the grps
					emp.getHashmap_module().put(String.valueOf(obj[1]), String.valueOf(obj[2])); // add module
					emp.getHashmap_prof().put(String.valueOf(obj[1]), String.valueOf(obj[3])); // add prof
					emplois.add(emp);
				}
				
				
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

		}
		for(Emploi emp1:emplois){
			System.out.println(emp1.getDate() + " "+ emp1.getHashmap_module()+ " "+emp1.getHashmap_prof()+ "  "+emp1.getHashmap_groupes());
			System.out.println("-----------------------------------------------------------");
		}
		return emplois;
	}
	
	private int getIndice(String date, List<Emploi> emplois) {
		for(Emploi emp : emplois) {
			if(emp.getDate().equals(date)) return emplois.indexOf(emp);
		}	
		return -1;
	}
	
	public List<Semaine> getSemaines(){
		List<Semaine> semaines = dao.getSemaine();
		return semaines;
	}
}
