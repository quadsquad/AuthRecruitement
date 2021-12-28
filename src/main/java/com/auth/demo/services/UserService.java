package com.auth.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.demo.entities.UserModel;
import com.auth.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired 
	private UserRepository userRepo;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		UserModel findByEmail=	userRepo.findByEmail(email);
			
		if(findByEmail == null)
			return null;
		String pwd=findByEmail.getPassword();
		String name=findByEmail.getEmail();
		
		return new User(name, pwd,   new ArrayList<>());
	}
	
	
	public UserModel getInfoUserConnected() throws UsernameNotFoundException {

		String connected =  SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel findByEmail=	userRepo.findByEmail(connected);

		if(findByEmail == null) 
			return null;
		String lastname = findByEmail.getLastname();
		String firstname = findByEmail.getFirstname();
		String email = findByEmail.getEmail();
		String userPicture = findByEmail.getUserPicture();
		String role = findByEmail.getRole();
		String phonenumber= findByEmail.getPhonenumber();
		String country= findByEmail.getCountry();
		String countryIso = findByEmail.getCountryIso();
		String city= findByEmail.getCity();
		String business_logo= findByEmail.getBusiness_logo();
	    String address = findByEmail.getAddress();
	    String business_website= findByEmail.getBusiness_website();
	    String business_name= findByEmail.getBusiness_name();
	    
		if (role.equals("particular")){
			return new UserModel(connected, role, userPicture, firstname, lastname, country, city);
		}
		return new UserModel(connected, phonenumber, country, countryIso, city, business_name, business_logo, business_website, address, role)	;	
	}


	
	
	
	
}
