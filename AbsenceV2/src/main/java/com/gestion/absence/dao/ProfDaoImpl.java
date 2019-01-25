package com.gestion.absence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gestion.absence.entities.Groupe;
import com.gestion.absence.entities.Module;
import com.gestion.absence.entities.Presence;
import com.gestion.absence.entities.Professeur;
import com.gestion.absence.entities.Seance;
import com.gestion.absence.entities.Semaine;
import com.gestion.absence.entities.Student;

public class ProfDaoImpl implements ProfDaoInterface {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Professeur getProf(String code, String password) {
		try {
			Query query = em.createQuery(" select p from Professeur p where p.cin=:x and p.password=:y");
			query.setParameter("x", code);
			query.setParameter("y", password);
			return (Professeur) query.getSingleResult() ;
		} catch (Exception e) {
			return null;
		}
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
	public List<String> getFiliers(String year){
		Query query = em.createQuery(" select distinct g.filiere from Groupe g where g.annee =:x ");
		query.setParameter("x", year);
		return query.getResultList();
	}

	@Override
	public List<Semaine> getSemaine() {
		Query query = em.createQuery("select s from Semaine s");
		return query.getResultList();
	}

	@Override
	public List<Module> getModules(int prof_id) {
		Query query = em.createQuery(" select m from Module m where m.professeur.id=:x");
		query.setParameter("x", prof_id);
		return query.getResultList();
	}

	@Override
	public List<Seance> getSeances(int module_id, int old) {
		Query query= null;
		if(old == 0) query = em.createQuery(" select s from Seance s where s.module.id=:x and s.id not in(select p.seance.id from Presence p)  ");
		else query = em.createQuery(" select s from Seance s where s.module.id=:x and s.id in(select p.seance.id from Presence p)  ");
		query.setParameter("x", module_id);
		return query.getResultList();
	}

	@Override
	public List<Student> getStudents(int seance_id) {
		Query query = em.createQuery(" select distinct(std) from Student std, Groupe g, Seances_grps sg, Seance s where std.groupe.id=sg.groupe.id and sg.seance.id=s.id and s.id=:x ");
		query.setParameter("x", seance_id);
		return query.getResultList();
	}

	@Override
	public Seance getSeance(int seance_id) {
		Query query = em.createQuery(" select s from Seance s where s.id=:x");
		query.setParameter("x", seance_id);
		return (Seance) query.getSingleResult();
	}

	@Override
	public void savePresence(Presence p) {
		em.persist(p);
	}

	@Override
	public Student getStudent(int student_id) {
		Query query = em.createQuery("select s from Student s where s.id=:x");
		query.setParameter("x", student_id);
		return (Student) query.getSingleResult();
	}

	@Override
	public List<Presence> getPresences(int seance_id) {
		Query query = em.createQuery(" select p from Presence p where p.seance.id=:x");
		query.setParameter("x", seance_id);
		return query.getResultList();
	}

	@Override
	public void updatePresence(Presence p) {
		try {
			Query query = em.createQuery(" update Presence p set p.present=:x, p.justified=:y where p.student.id=:std and p.seance.id=:sn ");
			query.setParameter("x", p.isPresent());
			query.setParameter("y", p.isJustified());
			query.setParameter("std", p.getStudent().getId());
			query.setParameter("sn", p.getSeance().getId());
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
	}

	@Override
	public List<Groupe> getGroupes(int prof_id) {
		Query query = em.createQuery(" select distinct(g) from Groupe g, Seances_grps sg, Seance s, Module m where g.id=sg.groupe.id and sg.seance.id=s.id and s.module.id=m.id and m.professeur.id=:x ");
		query.setParameter("x", prof_id);
		return query.getResultList();
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
}
