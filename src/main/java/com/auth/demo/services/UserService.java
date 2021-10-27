package com.auth.demo.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
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
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserModel findbyUsername=	userRepo.findByUsername(username);
			
		if(findbyUsername == null)
			return null;
		String pwd=findbyUsername.getPassword();
		String name=findbyUsername.getUsername();
		
		return new User(name, pwd,   new ArrayList<>());
	}
	
	
	public UserModel getInfoUserConnected() throws UsernameNotFoundException {

		String connected =  SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel findbyUsername=	userRepo.findByUsername(connected);

		if(findbyUsername == null) 
			return null;
		String role = findbyUsername.getRole();
		String fullname = findbyUsername.getFullname();
		String email = findbyUsername.getEmail();
		String age = findbyUsername.getAge();
		String userPicture = findbyUsername.getUserPicture();
		String speciality = findbyUsername.getSpeciality();
		String diploma = findbyUsername.getDiploma();
		if (role.equals("stagiaire")){
			return new UserModel(connected,fullname,email,age,userPicture,speciality,role);
		}
		return new UserModel(connected,fullname,email,age,userPicture,speciality,diploma,role);		
	}

	
	
	
	
}
