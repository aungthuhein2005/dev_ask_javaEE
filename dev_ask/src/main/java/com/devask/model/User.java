package com.devask.model;

import java.util.HashMap;

public class User {
	private int user_id;
	private String username;
	private String email;
	private String password;
	private int reputation;
	private String about;
	private String dob;
	private HashMap<String, String> socialList;
	private String createdAt;
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public User() {};
	
	public User(int user_id, String username, String email, String password, int reputation,String about,String dob, HashMap<String, String> socialList, String createdAt) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.reputation = reputation;
		this.about = about;
		this.socialList = socialList;
		this.createdAt = createdAt;
		this.dob = dob;
	}

	public User(String username, String email, String password, int reputation, String createdAt) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.reputation = reputation;
		this.createdAt = createdAt;
	}

	public User(int user_id, String username, String password, int reputation, String createdAt) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.reputation = reputation;
		this.createdAt = createdAt;
	}
	
	public User(int user_id, String username, int reputation,String about,String dob, HashMap<String, String> socialList) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.reputation = reputation;
		this.about = about;
		this.dob = dob;
		this.socialList = socialList;
	}
	
	public User( String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public User( String username,String email,String dob,String password) {
		super();
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.email = email;
	}
	
	public User(int userId, String username,String about,String dob) {
		super();
		this.user_id = userId;
		this.username = username;
		this.about = about;
		this.dob = dob;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public HashMap<String, String> getSocialList() {
		return socialList;
	}

	public void setSocialList(HashMap<String, String> socialList) {
		this.socialList = socialList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
