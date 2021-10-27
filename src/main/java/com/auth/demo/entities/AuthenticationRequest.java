package com.auth.demo.entities;

public class AuthenticationRequest {

	
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String role;
	private String age;	
	private String userPicture;
	private String diploma;
	private String speciality; //web, mobile, tronc commun, BI, ...

	// RECRUTEUR FIELDS : username, password, email, entreprise_name, entreprise_domaine, entreprise_logo
	private String entreprise_name;
	private String entreprise_domaine;
	private String entreprise_logo;

	
	public AuthenticationRequest() {
		   // TODO document why this constructor is empty
		 }
	
	public String getEntreprise_name() {
		return entreprise_name;
	}

	public void setEntreprise_name(String entreprise_name) {
		this.entreprise_name = entreprise_name;
	}

	public String getEntreprise_domaine() {
		return entreprise_domaine;
	}

	public void setEntreprise_domaine(String entreprise_domaine) {
		this.entreprise_domaine = entreprise_domaine;
	}

	public String getEntreprise_logo() {
		return entreprise_logo;
	}

	public void setEntreprise_logo(String entreprise_logo) {
		this.entreprise_logo = entreprise_logo;
	}
	

	
	public String getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getDiploma() {
		return diploma;
	}
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	
}
