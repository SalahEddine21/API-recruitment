package com.gestion.rh.offre;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Domain implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String domain;
}
