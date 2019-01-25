package com.gestion.absence.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Presence implements Serializable {
	
	@EmbeddedId
	private Presence_Pk id;
	
	@ManyToOne
	@MapsId("studentId")
	private Student student;
	
	@ManyToOne
	@MapsId("seanceId")
	private Seance seance;
	
	private boolean present;
	
	private boolean justified;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean isPresent) {
		this.present = isPresent;
	}

	public Presence_Pk getId() {
		return id;
	}

	public void setId(Presence_Pk id) {
		this.id = id;
	}

	public boolean isJustified() {
		return justified;
	}

	public void setJustified(boolean justified) {
		this.justified = justified;
	}
	
	

}
