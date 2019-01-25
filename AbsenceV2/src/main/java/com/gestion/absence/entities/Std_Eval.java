package com.gestion.absence.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Std_Eval implements Serializable {
	
	@EmbeddedId
	private StdEval_Pk id;
	
	@ManyToOne
	@MapsId("studentId")
	private Student student;
	
	@ManyToOne
	@MapsId("evalId")
	private Evaluation eval;
	
	// others fields
	private float note;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Evaluation getEval() {
		return eval;
	}

	public void setEval(Evaluation eval) {
		this.eval = eval;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	public StdEval_Pk getId() {
		return id;
	}

	public void setId(StdEval_Pk id) {
		this.id = id;
	}
	
	
	
	
}
