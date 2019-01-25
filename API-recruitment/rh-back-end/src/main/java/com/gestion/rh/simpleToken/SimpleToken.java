package com.gestion.rh.simpleToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.gestion.rh.candidate.Candidate;

@Entity
public class SimpleToken{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String tokenValue;
	
	private LocalDate startDate;
	private int EXPIRATION_TIME=172800;
	
	@ManyToOne
	private Candidate candidate;
	
	public SimpleToken() {
		super();
	}

	public SimpleToken(String tokenValue, LocalDate startDate, int eXPIRATION_TIME) {
		this.tokenValue = tokenValue;
		this.startDate = startDate;
		this.EXPIRATION_TIME = eXPIRATION_TIME;
	}


	public int getEXPIRATION_TIME() {
		return EXPIRATION_TIME;
	}

	public void setEXPIRATION_TIME(int eXPIRATION_TIME) {
		EXPIRATION_TIME = eXPIRATION_TIME;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}


	public boolean isValidToken(){
		Period period = Period.between(this.startDate, LocalDate.now());
		Date date = Date.from(this.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); //convert LocalDate to Date
		LocalDateTime cd=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();  //convert Date to LocalDateTime
		long seconds = ChronoUnit.SECONDS.between(cd, LocalDateTime.now());
		if(period.getYears()==0 && period.getMonths()==0 && period.getDays()==0 && seconds<this.getEXPIRATION_TIME()) return true;
		return false;
	}
}
