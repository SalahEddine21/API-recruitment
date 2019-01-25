package com.gestion.absence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Module implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Titre")
	private String titre;
	
	@Column(name="Semestre")
	private String semestre;
		
	@ManyToMany
	@JoinTable(name="Programme", joinColumns=@JoinColumn(name="module_id"), inverseJoinColumns=@JoinColumn(name="groupe_id"))
	private List<Groupe> groupes;
	
	@OneToMany(mappedBy="module")
	private List<Seance> seances;
	
	@ManyToOne
	@JoinColumn(name="Professeur_id")
	private Professeur professeur;
	
	@OneToMany(mappedBy="module")
	private List<Evaluation> evls;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public List<Seance> getSeances() {
		return seances;
	}

	public void setSeances(List<Seance> seances) {
		this.seances = seances;
	}

	public Professeur getProfesseur() {
		return professeur;
	}

	public void setProfesseur(Professeur professeur) {
		this.professeur = professeur;
	}

	public List<Evaluation> getEvls() {
		return evls;
	}

	public void setEvls(List<Evaluation> evls) {
		this.evls = evls;
	}
	
	
}
