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

@Column(unique= true)
private String username;
private String password;
private String fullname;
@Column(unique=true)
private String email;
private String role;
private String age;
private String userPicture;
private String diploma;
private String speciality; //web, mobile, tronc commun, BI, ...


//ADMIN FIELDS : fullname, email, username, password, userPicture

// RECRUTEUR FIELDS : username, password, email, entreprise_name, entreprise_domaine, entreprise_logo
private String entreprise_name;
private String entreprise_domaine;
private String entreprise_logo;


//attr en comm : username, fullname,email, age,speciality, role

//ADMIN CONSTRUCTOR
public UserModel(String username, String fullname, String email, String userPicture, String role) {
super();
this.username = username;
this.fullname = fullname;
this.email = email;
this.userPicture = userPicture;
this.role = role;
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

public String getDiploma() {
return diploma;
}

public void setDiploma(String diploma) {
this.diploma = diploma;
}

public String getAge() {
return age;
}

public void setAge(String age) {
this.age = age;
}

public String getSpeciality() {
return speciality;
}

public void setSpeciality(String speciality) {
this.speciality = speciality;
}


public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
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

public String getEmail(){
return email;
}

public void setEmail(String email){
this.email= email;
}


//INTERNSHIP CONSTRUCTOR

public UserModel(String username, String fullname, String email, String age, String userPicture,String speciality, String role) {
super();
this.username = username;
this.fullname = fullname;
this.email = email;
this.age = age;
this.userPicture = userPicture;
this.speciality = speciality;
this.role = role;
}

//EMPLOYEE CONSTRUCTOR
public UserModel(String username, String fullname, String email, String age,String userPicture, String speciality, String diploma, String role) {
super();
this.username = username;
this.fullname = fullname;
this.email = email;
this.age = age;
this.userPicture = userPicture;
this.speciality = speciality;
this.diploma = diploma;
this.role = role;
}


//RECRUIT CONSTRUCTOR
public UserModel(String username, String email, String entreprise_name, String entreprise_domaine,
String entreprise_logo, String role) {
super();
this.username = username;
this.email = email;
this.entreprise_name = entreprise_name;
this.entreprise_domaine = entreprise_domaine;
this.entreprise_logo = entreprise_logo;
this.role = role;

}

public UserModel() {
// TODO Auto-generated constructor stub
}




}

