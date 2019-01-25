package com.gestion.absence.metier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.absence.Beans.ApplicationContextProvider;
import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.dao.ProfDaoInterface;
import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Presence_Pk;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

@Transactional
public class ProfMetier {

	private ProfDaoInterface dao;
	
	@Autowired
	private ApplicationContextProvider provider;
		
	public ProfDaoInterface getDao() {
		return dao;
	}

	public void setDao(ProfDaoInterface dao) {
		this.dao = dao;
	}

	public Professeur getProf(String cin, String password) {
		return dao.getProf(cin, password);
	}
	
	public List<String> getFiliers(String year){
		List<String> filiers = dao.getFiliers(year);
		return filiers;
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
	
	public List<Module> getModules(int prof_id){
		return dao.getModules(prof_id);
	}
	
	public List<Seance> getSeances(int module_id, int old){
		return dao.getSeances(module_id, old);
	}
	
	public List<HashedMap<String, List<Student>>> getGroupes(int seance_id){
		List<HashedMap<String, List<Student>>> grps = new ArrayList<HashedMap<String, List<Student>>>();
		List<Student> stds = dao.getStudents(seance_id);
		HashedMap<String, List<Student>> groupe;
		List<Student> list;
		for(Student s: stds) {
			list = getGroupe(grps, s.getGroupe().getName());
			if(list == null) {
				 groupe = new HashedMap<String, List<Student>>();
				 list = new ArrayList<Student>();
				 list.add(s);
				 groupe.put(s.getGroupe().getName(), list);
				 grps.add(groupe);
			}else list.add(s);
		}
		return grps;
	}
	
	private List<Student> getGroupe(List<HashedMap<String, List<Student>>> grps, String name){
		for(HashedMap<String, List<Student>> groupe : grps) {
			if(groupe.get(name) != null) return groupe.get(name);
		}
		return null;
	}
	
	public void savePresence(List<HashedMap<String, List<Student>>> grps, Map<String,String> params, int seance_id) {
		Seance s = dao.getSeance(seance_id);
		List<Student> stds;
		Student ndstd;
		Presence presence;
		for(HashedMap<String, List<Student>> groupe : grps) {
			System.out.println("-----------------------------------------------------");
			for(Map.Entry<String, List<Student>> m : groupe.entrySet()) {
				System.out.println("Groupe name is: "+m.getKey());
				stds = m.getValue();
				for(Student std: stds) {
					ndstd = dao.getStudent(std.getId());
					presence = (Presence) provider.getApplicationContext().getBean("presence", Presence.class);
					
					presence.setId(new Presence_Pk(std.getId(), s.getId()));
					presence.setStudent(ndstd);
					presence.setSeance(s);
					presence.setPresent(params.get("p"+std.getId()) != null ? true : false);
					presence.setJustified(params.get("j"+std.getId()) != null ? true : false);
					dao.savePresence(presence);
				}
			}
		}
	}
	
	public List<HashedMap<String, List<Presence>>> getPresences(int seance_id){
		List<Presence> prs = dao.getPresences(seance_id);
		List<HashedMap<String, List<Presence>>> grps = new ArrayList<HashedMap<String, List<Presence>>>();
		HashedMap<String, List<Presence>> groupe;
		List<Presence> list;
		
		for(Presence p : prs) {
			list = getPresence(grps, p.getStudent().getGroupe().getName());
			if(list == null) {
				 groupe = new HashedMap<String, List<Presence>>();
				 list = new ArrayList<Presence>();
				 list.add(p);
				 groupe.put(p.getStudent().getGroupe().getName(), list);
				 grps.add(groupe);
			}else list.add(p);
		}
		return grps;
	}
	
	private List<Presence> getPresence(List<HashedMap<String, List<Presence>>> grps, String name){
		for(HashedMap<String, List<Presence>> groupe : grps) {
			if(groupe.get(name) != null) return groupe.get(name);
		}
		return null;
	}
	
	public String updatePresence(List<HashedMap<String, List<Presence>>> prs, Map<String,String> params) {
		List<Presence> presence;
		for(HashedMap<String, List<Presence>> groupe : prs) {
			for(Map.Entry<String, List<Presence>> m : groupe.entrySet()) {
				presence = m.getValue();
				for(Presence pr: presence) {
					pr.setPresent(params.get("p"+pr.getStudent().getId()) != null ? true : false);
					pr.setJustified(params.get("j"+pr.getStudent().getId()) != null ? true : false);
					dao.updatePresence(pr);
				}
			}
		}
		return "done";
	}
	
	public List<Groupe> getGroupes_prof(int prof_id){
		return dao.getGroupes(prof_id);
	}
	
	public List<Presence> getPresence(int std_id) {
		return dao.getPresence(std_id);
	}
	
	public Student getStudent(int std_id) {
		return dao.getStudent(std_id);
	}
}
