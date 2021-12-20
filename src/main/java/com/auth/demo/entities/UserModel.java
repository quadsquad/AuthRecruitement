package com.auth.demo.entities;


import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class UserModel {

@Id
private String id;
@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)

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
private String city;
private boolean isEnabled;

//@Column(unique=true)
//private String username;

//ADMIN FIELDS : fullname, email, username, password, userPicture

// RECRUTEUR FIELDS : username, password, email, entreprise_name, entreprise_domaine, entreprise_logo
private String business_name;
private String business_logo;
private String business_website;
private String address;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
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
	this.confirmpassword = confirmpassword;
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
public UserModel() {
	super();
	// TODO Auto-generated constructor stub
}
public UserModel(  String email, String phonenumber, String country,
		String city, String business_name, String business_logo, String business_website, String address , String role) {
	super();

	this.email = email;
	this.phonenumber = phonenumber;
	this.country = country;
	this.city = city;
	this.business_name = business_name;
	this.business_logo = business_logo;
	this.business_website = business_website;
	this.address = address;
	this.role = role;

}
public UserModel(  String email, String role, String userPicture,
		String firstname, String lastname, String country, String city) {
	super();
	this.email = email;
	this.role = role;
	this.userPicture = userPicture;
	this.firstname = firstname;
	this.lastname = lastname;
	this.country = country;
	this.city = city;

}
public boolean isEnabled() {
	return isEnabled;
}
public void setEnabled(boolean isEnabled) {
	this.isEnabled = isEnabled;
}


}

