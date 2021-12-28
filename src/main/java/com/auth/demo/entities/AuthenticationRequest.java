package com.auth.demo.entities;

import javax.persistence.Column;

public class AuthenticationRequest {

private String password;
private String confirmpassword;
@Column(unique=true)
private String email;
private String role;
private String userPicture;
private String phonenumber;
private String firstname;
private String lastname;
private String country;
private String codeCountry;
private String countryIso;
private String city;
//ADMIN FIELDS : fullname, email, username, password, userPicture

// RECRUTEUR FIELDS : username, password, email, entreprise_name, entreprise_domaine, entreprise_logo
private String business_name;
private String business_logo;
private String business_website;
private String address;

public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getConfirmpassword() {
	return confirmpassword;
}
public void setConfirmpassword(String confirmpassword) {
	confirmpassword = confirmpassword;
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
public String getUserPicture() {
	return userPicture;
}
public void setUserPicture(String userPicture) {
	this.userPicture = userPicture;
}
public String getPhonenumber() {
	return phonenumber;
}
public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getBusiness_name() {
	return business_name;
}
public void setBusiness_name(String business_name) {
	this.business_name = business_name;
}
public String getBusiness_logo() {
	return business_logo;
}
public void setBusiness_logo(String business_logo) {
	this.business_logo = business_logo;
}
public String getBusiness_website() {
	return business_website;
}
public void setBusiness_website(String business_website) {
	this.business_website = business_website;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCountryIso() {
	return countryIso;
}
public void setCountryIso(String countryIso) {
	this.countryIso = countryIso;
}
public String getCodeCountry() {
	return codeCountry;
}
public void setCodeCountry(String codeCountry) {
	this.codeCountry = codeCountry;
}
}
