package com.gestion.absence.Beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Emploi {

	private String date;
	private HashMap<String, String> hashmap_module = new HashMap<String, String>();
	private HashMap<String, String> hashmap_prof = new HashMap<String, String>();
	private HashMap<String, List<String>> hashmap_groupes = new HashMap<String, List<String>>();
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public HashMap<String, String> getHashmap_module() {
		return hashmap_module;
	}
	public void setHashmap_module(HashMap<String, String> hashmap_module) {
		this.hashmap_module = hashmap_module;
	}
	public HashMap<String, String> getHashmap_prof() {
		return hashmap_prof;
	}
	public void setHashmap_prof(HashMap<String, String> hashmap_prof) {
		this.hashmap_prof = hashmap_prof;
	}
	public HashMap<String, List<String>> getHashmap_groupes() {
		return hashmap_groupes;
	}
	public void setHashmap_groupes(HashMap<String, List<String>> hashmap_groupes) {
		this.hashmap_groupes = hashmap_groupes;
	}
	
	
}
