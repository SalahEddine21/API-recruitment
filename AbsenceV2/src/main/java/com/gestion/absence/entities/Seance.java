package com.gestion.absence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class Seance implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Date")
	private Date date;
	
	@Column(name="Heure")
	private String heure;
	
	@OneToMany(mappedBy="seance")
	private List<Presence> presences;
	
	@OneToMany(mappedBy="seance", cascade=CascadeType.ALL)
	private List<Seances_grps> groupes;
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module;
	
	@ManyToOne
	@JoinColumn(name="semaine_id")
	private Semaine semaine;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public List<Presence> getPresences() {
		return presences;
	}

	public void setPresences(List<Presence> presences) {
		this.presences = presences;
	}

	public List<Seances_grps> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Seances_grps> groupes) {
		this.groupes = groupes;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Semaine getSemaine() {
		return semaine;
	}

	public void setSemaine(Semaine semaine) {
		this.semaine = semaine;
	}
	
	
}
