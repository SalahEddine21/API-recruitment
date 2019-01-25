package com.gestion.absence.metier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.absence.Beans.ApplicationContextProvider;
import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.dao.AdminDaoInterface;
import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Seances_grps;
import com.gestion.absence.entities.Seances_grpsPk;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

@Transactional
public class AdminMetier{

	@Autowired
	private ApplicationContextProvider provider;
	
	private AdminDaoInterface dao;

	public AdminDaoInterface getDao() {
		return dao;
	}

	public void setDao(AdminDaoInterface dao) {
		this.dao = dao;
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
	
	public String saveEmploi(String path, String year, String option, String date_debut, String date_fin, String semaine) {
		
        FileInputStream input;
		try {
			Seance s;
			Cell date_cell, heure_cell, module_cell, groupes_cell;
			SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd"); // swap to the xml spring file
			SimpleDateFormat ndf1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy"); // swap to the xml spring file
			String groupes;
			String[] list_grps;
			List<Seances_grps> list;
			Module m;
			Seances_grps sg;
			Groupe g;
			Semaine sm;
			
			input = new FileInputStream(path);
	        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input);    
	        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
	        Iterator<Row> rowIterator = my_worksheet.iterator(); 
	        sm = (Semaine) provider.getApplicationContext().getBean("semaine", Semaine.class);
	        sm.setName(semaine);
	        sm.setDate_debut(ndf.parse(date_debut));
	        sm.setDate_fin( ndf.parse(date_fin));
	        sm = dao.saveSemaine(sm); // save the new week
	        while(rowIterator.hasNext()) {
	            Row row = rowIterator.next();   
	            Iterator<Cell> cellIterator = row.cellIterator();
	            s = (Seance) provider.getApplicationContext().getBean("seance", Seance.class);
	            
	            date_cell = cellIterator.next(); // date seance
	            
	            s.setDate(date_cell.getDateCellValue());
	            
	            heure_cell = cellIterator.next(); // heure seance
	            s.setHeure(heure_cell.getStringCellValue());
	            
	            module_cell = cellIterator.next(); // module 
	            m = dao.getModule(module_cell.getStringCellValue());
	            s.setModule(m);
	            	
	            
	            groupes_cell = cellIterator.next(); // groupes
	            groupes = groupes_cell.getStringCellValue();
	            list_grps = groupes.split("/");
	            list = new ArrayList<Seances_grps>();
	            
	            for(int i=0; i<list_grps.length; i++) { // add the active seance to each groupe 
	            	g = dao.getGroupe(list_grps[i], year, option);
	            	//if(g == null) return "Exception: groupe's name "+list_grps[i] + ", year: "+year+ " is not found on the database";
	            	sg = (Seances_grps) provider.getApplicationContext().getBean("sgrp", Seances_grps.class);
	            	sg.setId(new Seances_grpsPk(s.getId(), g.getId()));
	            	sg.setGroupe(g);
	            	sg.setSeance(s);
	            	list.add(sg);
	            }
	            s.setSemaine(sm);
	            s.setGroupes(list); // add the list of groupes to seance object
	            
	            dao.saveSeance(s);	  
	            
	        }
	        input.close(); 
	        my_xls_workbook.close();
	        return "done";
		} catch (Exception e) {
			System.out.println("EXCEPTION: "+e.getMessage());
			return "";
		}
		
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
	
	public String saveGroupe(String f, String a, String s, String n, String file_path) {
		Groupe g = (Groupe) provider.getApplicationContext().getBean("groupe", Groupe.class);
		g.setName(n);
		g.setFiliere(f);
		g.setAnnee(a);
		g.setAnnee_scolaire(s);
		
		g = dao.addGroups(g);
		String code, name, lastname, email, passe;
		Student std;
		FileInputStream input;
		
		try {
			input = new FileInputStream(file_path);
	        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input);    
	        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
	        Iterator<Row> rowIterator = my_worksheet.iterator(); 
	        
	        while(rowIterator.hasNext()) {
	        	Row row = rowIterator.next();   
	            Iterator<Cell> cellIterator = row.cellIterator();
	            
	            code = cellIterator.next().getStringCellValue();
	            name = cellIterator.next().getStringCellValue();
	            lastname = cellIterator.next().getStringCellValue();
	            email = cellIterator.next().getStringCellValue();
	            passe = cellIterator.next().getStringCellValue();
	            
	            std = (Student) provider.getApplicationContext().getBean("student", Student.class); // to get the bean class
	            std.setCode(code);
	            std.setName(name);
	            std.setLastName(lastname);
	            std.setEmail(email);
	            std.setPassword(passe);
	            std.setGroupe(g);
	            
	            dao.saveStudent(std);
	        }
	        input.close(); 
	        my_xls_workbook.close();
	        return "done";
	        
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
	}
	
	public List<Groupe> getGroupes(){
		List<Groupe> grps = dao.getGroupes();
		return grps;
	}
	
	public List<Student> getStudents(int groupe){
		List<Student> stds = dao.getStudents(groupe);
		return stds;
	}
	
	public String LoadProfs(String path_file) {
		FileInputStream input;
		Professeur p;
		String cin, name, lastname, email, password;
		
		try {
			input = new FileInputStream(path_file);
	        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input);    
	        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
	        Iterator<Row> rowIterator = my_worksheet.iterator(); 
	        while(rowIterator.hasNext()) {
	        	Row row = rowIterator.next();   
	            Iterator<Cell> cellIterator = row.cellIterator();
	            
	            p = (Professeur) provider.getApplicationContext().getBean("prof", Professeur.class);
	            
	            cin = cellIterator.next().getStringCellValue();
	            name = cellIterator.next().getStringCellValue();
	            lastname = cellIterator.next().getStringCellValue();
	            email = cellIterator.next().getStringCellValue();
	            password = cellIterator.next().getStringCellValue();
	            
	            p.setCin(cin);
	            p.setName(name);
	            p.setLastName(lastname);
	            p.setEmail(email);
	            p.setPassword(password);
	            
	            dao.saveProf(p);
	        }
	        input.close(); 
	        my_xls_workbook.close();
	        return "done";
		} catch (Exception e) {
			return "Excpetion: "+e.getMessage();
		}
	}
	
	public List<Professeur> getDataProfs(int start){
		List<Professeur> profs = dao.getDataProfs(start);
		List<Module> modules;
		for(Professeur prof : profs) {
			modules = dao.getModuleProf(prof.getId());
			prof.setModules(modules);
		}
		return profs;
	}
	
	public long countProfs() {
		return dao.CountProfs();
	}
	
	public String loadModules(String file_path) {
		FileInputStream input;
		String titre, semestre, prof, data_prof[];
		Module m;
		Professeur p;
		try {
			input = new FileInputStream(file_path);
	        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input);    
	        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
	        Iterator<Row> rowIterator = my_worksheet.iterator(); 	
	        while(rowIterator.hasNext()) {
	        	Row row = rowIterator.next();   
	            Iterator<Cell> cellIterator = row.cellIterator();
	            
	            titre = cellIterator.next().getStringCellValue();
	            semestre = cellIterator.next().getStringCellValue();
	            prof = cellIterator.next().getStringCellValue();
	            data_prof = prof.split("\\s");
	            
	            p  = dao.getProf(data_prof[0], data_prof[1]);
	            m = (Module) provider.getApplicationContext().getBean("module", Module.class);
	            
	            m.setTitre(titre);
	            m.setSemestre(semestre);
	            m.setProfesseur(p);
	            
	            dao.saveModule(m);
	        }
	        input.close(); 
	        my_xls_workbook.close();   
	        return "done";
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
	}
	
	public List<Module> getModule(String semestre){
		return dao.getModules(semestre);
	}
	
	public List<Presence> getPresence(int std_id) {
		return dao.getPresence(std_id);
	}
	
	public Student getStudent(int std_id) {
		return dao.getStudent(std_id);
	}
}
