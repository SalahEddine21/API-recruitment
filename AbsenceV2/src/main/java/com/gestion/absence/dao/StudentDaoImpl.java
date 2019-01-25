package com.gestion.absence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

public class StudentDaoImpl implements StudentDaoInterface {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Student getStudent(String code, String password) {
		try {
			Query query = em.createQuery(" select s from Student s where s.code=:x and s.password=:y");
			query.setParameter("x", code);
			query.setParameter("y", password);
			return (Student) query.getSingleResult() ;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Presence> getPresences(int student_id) {
		Query query = em.createQuery(" select p from Presence p where p.student.id=:x");
		query.setParameter("x", student_id);
		return query.getResultList();
	}

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
	public List<Semaine> getSemaine() {
		Query query = em.createQuery("select s from Semaine s");
		return query.getResultList();
	}
}
