package com.gestion.absence.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gestion.absence.Beans.Emploi;
import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Seances_grps;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

public class AdminDaoImpl implements AdminDaoInterface {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Object> getEmp(String semaine, String year, String option){
		Query query = null;
		if(!year.equals("1A")) {
			query = em.createQuery(" select s.date as date, s.heure as heure, m.titre as module, p.name as professeur, g.name as groupe from Seance as s, Module m, Professeur p, Groupe g, Semaine sm, Seances_grps as sg where s.semaine.id = sm.id and sm.name=:x and s.id = sg.id.seanceID and sg.id.groupeID = g.id and g.annee =:y and g.filiere=:z and s.module.id = m.id and m.professeur.id = p.id order by s.date, s.heure");
			query.setParameter("x", semaine);
			query.setParameter("z", option);
			query.setParameter("y", year);
		}
		else {
			query = em.createQuery(" select s.date as date, s.heure as heure, m.titre as module, p.name as professeur, g.name as groupe from Seance as s, Module m, Professeur p, Groupe g, Semaine sm, Seances_grps as sg where s.semaine.id = sm.id and sm.name=:x and s.id = sg.id.seanceID and sg.id.groupeID = g.id and g.annee =:y and s.module.id = m.id and m.professeur.id = p.id order by s.date, s.heure");
			query.setParameter("x", semaine);
			query.setParameter("y", year);
		}
		return query.getResultList();
	}

	@Override
	public List<String> getFiliers(String year){
		Query query = em.createQuery(" select distinct g.filiere from Groupe g where g.annee =:x ");
		query.setParameter("x", year);
		return query.getResultList();
	}

	@Override
	public Seance saveSeance(Seance s) {
		em.persist(s);
		return s;
	}

	@Override
	public Module getModule(String titre) {
		Query query = em.createQuery(" select m from Module m where m.titre =:x");
		query.setParameter("x", titre);
		Module m = (Module) query.getSingleResult();
		return m;
	}

	@Override
	public Semaine getSemaine(Date date_debut, Date date_fin) {
		Query query = em.createQuery(" select s from Semaine s where s.date_debut >= :x and s.date_fin <= :y ");
		query.setParameter("x", date_debut);
		query.setParameter("y", date_fin);
		Semaine s = (Semaine) query.getSingleResult();
		return s;
	}

	@Override
	public void saveSeanceTogrp(Seances_grps sgrp) {
		em.persist(sgrp);
	}

	@Override
	public Groupe getGroupe(String name, String year, String option) {
		Query query = null;
		if(year.equals("1A")) {
			query = em.createQuery(" select g from Groupe g where g.name =:x and g.annee=:y ");
			query.setParameter("x", name);
			query.setParameter("y", year);
		}
		else {
			query = em.createQuery(" select g from Groupe g where g.name =:x and g.annee=:y and g.filiere=:z");
			query.setParameter("x", name);
			query.setParameter("y", year);
			query.setParameter("z", option);
		}
		Groupe g = (Groupe) query.getSingleResult();
		return g;
	}

	@Override
	public List<Semaine> getSemaine() {
		Query query = em.createQuery("select s from Semaine s");
		return query.getResultList();
	}

	@Override
	public Groupe addGroups(Groupe g) {
		em.persist(g);
		return g;
	}

	@Override
	public void saveStudent(Student s) {
		em.persist(s);
	}

	@Override
	public List<Groupe> getGroupes() {
		Query query = em.createQuery("select g from Groupe g");
		return query.getResultList();
	}

	@Override
	public List<Student> getStudents(int groupe) {
		Query query = em.createQuery("select s from Student s where s.groupe.id =:x order by s.name, s.lastName");
		query.setParameter("x", groupe);
		return query.getResultList();
	}

	@Override
	public void saveProf(Professeur p) {
		em.persist(p);
	}

	@Override
	public List<Professeur> getDataProfs(int start) {
		Query query = em.createQuery(" select p from Professeur p");
		query.setFirstResult(start);
		query.setMaxResults(start+5);
		return query.getResultList();
	}

	@Override
	public List<Module> getModuleProf(int id_prof) {
		Query query = em.createQuery(" select m from Module m where m.professeur.id =:x ");
		query.setParameter("x", id_prof);
		return query.getResultList();
	}

	@Override
	public long CountProfs() {
		Query query = em.createQuery("select count(p) from Professeur p ");
		long number = (long) query.getSingleResult();
		return number;
	}

	@Override
	public Professeur getProf(String name, String lastname) throws Exception{
		try {
			Query query = em.createQuery(" select p from Professeur p where p.name=:x and p.lastName =:y ");
			query.setParameter("x", name);
			query.setParameter("y", lastname);
			return (Professeur) query.getSingleResult();
		} catch (Exception e) {
			throw new Exception("Exception: "+e.getMessage());
		}
	}

	@Override
	public void saveModule(Module m) {
		em.persist(m);
	}

	@Override
	public List<Module> getModules(String semestre) {
		Query query = em.createQuery(" select m from Module m where m.semestre=:x");
		query.setParameter("x", semestre);
		return query.getResultList();
	}

	@Override
	public Semaine saveSemaine(Semaine sm) {
		em.persist(sm);
		return sm;
	}

	@Override
	public List<Presence> getPresence(int std_id) {
		try {
			Query query = em.createQuery(" select p from Presence p where p.student.id=:x");
			query.setParameter("x", std_id);
			return query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Student getStudent(int std_id) {
		Query query = em.createQuery(" select s from Student s where s.id=:x");
		query.setParameter("x", std_id);
		return (Student) query.getSingleResult();
	}
	
	
}
