package com.gestion.absence.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StdEval_Pk implements Serializable {

	@Column(name="Student_id")
	private int studentId;
	
	@Column(name="Eval_id")
	private int evalId;

	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        StdEval_Pk that = (StdEval_Pk) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(evalId, that.evalId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(studentId, evalId);
    }

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getEvalId() {
		return evalId;
	}

	public void setEvalId(int evalId) {
		this.evalId = evalId;
	}
	
    
}
