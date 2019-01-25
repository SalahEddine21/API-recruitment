package com.gestion.absence.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Seances_grps implements Serializable {

	@EmbeddedId
	private Seances_grpsPk id;
	
	@ManyToOne
	@MapsId("seanceID")
	private Seance seance;
	
	@ManyToOne
	@MapsId("groupeID")
	private Groupe groupe;

	public Seances_grpsPk getId() {
		return id;
	}

	public void setId(Seances_grpsPk id) {
		this.id = id;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	
	
}
