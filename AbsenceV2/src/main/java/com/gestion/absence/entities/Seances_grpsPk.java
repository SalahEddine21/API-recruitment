package com.gestion.absence.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Seances_grpsPk implements Serializable {

	@Column(name="seance_id")
	private int seanceID;
	
	@Column(name="groupe_id")
	private int groupeID;
	
	public Seances_grpsPk() {
		
	}
	
	public Seances_grpsPk(int seanceID, int groupeID) {
		this.seanceID = seanceID;
		this.groupeID = groupeID;
	}

	public int getSeanceID() {
		return seanceID;
	}

	public void setSeanceID(int seanceID) {
		this.seanceID = seanceID;
	}

	public int getGroupeID() {
		return groupeID;
	}

	public void setGroupeID(int groupeID) {
		this.groupeID = groupeID;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        Seances_grpsPk that = (Seances_grpsPk) o;
        return Objects.equals(seanceID, that.seanceID) && Objects.equals(groupeID, that.groupeID);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(seanceID, groupeID);
    }
	
}
