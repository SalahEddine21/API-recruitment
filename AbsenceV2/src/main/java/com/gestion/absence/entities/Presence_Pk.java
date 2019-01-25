package com.gestion.absence.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Presence_Pk implements Serializable {
	
	@Column(name="Student_id")
	private int studentId;
	
	@Column(name="seance_id")
	private int seanceId;
	
	public Presence_Pk() {}
	
	public Presence_Pk(int std_id, int seance_id) {
		this.seanceId = std_id;
		this.seanceId = seance_id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSeanceId() {
		return seanceId;
	}

	public void setSeanceId(int seanceId) {
		this.seanceId = seanceId;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        Presence_Pk that = (Presence_Pk) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(seanceId, that.seanceId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(studentId, seanceId);
    }

}
