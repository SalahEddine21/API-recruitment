package com.gestion.absence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Groupe implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
		
	@Column(name="Annee")
	private String annee;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="filiere")
	private String filiere;
	
	@Column(name="Annee_scolaire")
	private String annee_scolaire;
	
	@OneToMany(mappedBy="groupe")
	private List<Seances_grps> seances;
	
	@OneToMany(mappedBy="groupe")
	private List<Student> students;
	
	@ManyToMany(mappedBy="groupes")
	private List<Module> modules;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getAnnee_scolaire() {
		return annee_scolaire;
	}

	public void setAnnee_scolaire(String annee_scolaire) {
		this.annee_scolaire = annee_scolaire;
	}

	public List<Seances_grps> getSeances() {
		return seances;
	}

	public void setSeances(List<Seances_grps> seances) {
		this.seances = seances;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	
	
	
}
