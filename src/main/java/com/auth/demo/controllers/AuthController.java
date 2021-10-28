package com.auth.demo.controllers;


import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.demo.entities.AuthenticationRequest;
import com.auth.demo.entities.AuthenticationResponse;
import com.auth.demo.entities.UserModel;
import com.auth.demo.repositories.UserRepository;
import com.auth.demo.services.JwtUtils;
import com.auth.demo.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {


@Autowired
private PasswordEncoder passwordencoder;

@Autowired
private UserRepository userRepository;

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private UserService userService;

@Autowired
private JwtUtils jwtUtils;

@GetMapping("/content")
private String testingToken(AuthenticationRequest authenticationrequest, UserModel usermodel, String authenticationResponse){

 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
     String username = loggedInUser.getName();
     UserModel user = userRepository.findByUsername(username);
     String roleauth=user.getRole();
    /* if(roleauth =="recruiteur"){
      return "Welcome "+ user.getRole()+ "to your dashboard";
     }else {
         return "Access denied Mr le "+roleauth;}*/
   
         return "Welcome "+ user.getRole()+ " to your dashboard";
     
    	 }

@GetMapping("/getToken")
private ResponseEntity<?> getToken(AuthenticationRequest authenticationrequest, UserModel usermodel, String authenticationResponse){

	 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	     String username = loggedInUser.getName();
	     UserModel user = userRepository.findByUsername(username);
	    // String role = user.getRole();
	     	
	     return new ResponseEntity<>(username, HttpStatus.ACCEPTED);  	
       
}

@GetMapping("/findallusers")
public ResponseEntity<?> getAllUsers(){
	List<UserModel> users =	userRepository.findAll();
	if(!users .isEmpty()){
		return new ResponseEntity<>(users , HttpStatus.OK);
		
		}
	else 
	{
			return new ResponseEntity<String>("No users  Available",HttpStatus.NOT_FOUND);
	}
}


@GetMapping("/getuserconnected")
private UserModel getuser(AuthenticationRequest authenticationrequest, UserModel usermodel){

 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
     String username = loggedInUser.getName();
     UserModel user = userRepository.findByUsername(username);
     String fullname = user.getFullname();
     String email = user.getEmail();
     String age = user.getAge();
     String userPicture = user.getUserPicture();
     String speciality = user.getSpeciality();
     String diploma = user.getDiploma();
   String entreprise_name = user.getEntreprise_name();
 String entreprise_domaine= user.getEntreprise_domaine();
 String entreprise_logo = user.getEntreprise_logo();

     String role = user.getRole();
     if (role.equals("stagiaire")){
return new UserModel(username,fullname,email,age,userPicture,speciality,role);
}else if(role.equals("employee"))
{
return new UserModel(username,fullname,email,age,userPicture,speciality,diploma,role);
}
else if(role.equals("recruiteur"))
{
return new UserModel(username,email,entreprise_name,entreprise_domaine,entreprise_logo,role);
}else{
return new UserModel(username,fullname,email,userPicture,role);
}
}

//Subscribe
@PostMapping("/subscribe")
private ResponseEntity<?> subscribeClient(@RequestBody AuthenticationRequest authenticationRequest){
String pwd= authenticationRequest.getPassword();
String username= authenticationRequest.getUsername();
String fullname = authenticationRequest.getFullname();
String email = authenticationRequest.getEmail();
String role = authenticationRequest.getRole();
String age = authenticationRequest.getAge();
String speciality = authenticationRequest.getSpeciality();
String diploma = authenticationRequest.getDiploma();
String entreprise_name = authenticationRequest.getEntreprise_name();
String entreprise_domaine= authenticationRequest.getEntreprise_domaine();
String entreprise_logo = authenticationRequest.getEntreprise_logo();

UserModel userModel= new UserModel();
if(role.equals("stagiaire"))
{
userModel.setUsername(username);
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setFullname(fullname);
userModel.setEmail(email);
userModel.setAge(age);
userModel.setUserPicture("https://e7.pngegg.com/pngimages/505/761/png-clipart-login-computer-icons-avatar-icon-monochrome-black-thumbnail.png");
userModel.setSpeciality(speciality);
userModel.setRole(role);
} else if (role.equals("employee")) {
userModel.setUsername(username);
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setEmail(email);
userModel.setFullname(fullname);
userModel.setRole(role);
userModel.setAge(age);
userModel.setUserPicture("https://e7.pngegg.com/pngimages/505/761/png-clipart-login-computer-icons-avatar-icon-monochrome-black-thumbnail.png");
userModel.setSpeciality(speciality);
userModel.setDiploma(diploma);
} else if (role.equals("recruiteur"))
{
userModel.setUsername(username);
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setEmail(email);
userModel.setEntreprise_name(entreprise_name);
userModel.setEntreprise_domaine(entreprise_domaine);
userModel.setEntreprise_logo(entreprise_logo);
userModel.setRole(role);
//userModel.setAge((Integer) null);

}else if (role.equals("admin")) {
userModel.setFullname(fullname);
userModel.setEmail(email);
userModel.setUsername(username);
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setUserPicture("https://e7.pngegg.com/pngimages/505/761/png-clipart-login-computer-icons-avatar-icon-monochrome-black-thumbnail.png");
userModel.setRole(role);
// userModel.setAge((Integer) null);

}
else {
System.err.println();
}

try {
userRepository.save(userModel);

}catch(Exception e){
return ResponseEntity.ok(new AuthenticationResponse("Error during subscription"+username));

}
return ResponseEntity.ok(new AuthenticationResponse("Successful subscription"+username));

}

@PostMapping("/auth")
private ResponseEntity<?> authenticateClient(@RequestBody AuthenticationRequest authenticationRequest){
String username= authenticationRequest.getUsername();
String password= authenticationRequest.getPassword();


try {
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

}catch(Exception e){
return ResponseEntity.ok(new AuthenticationResponse(e.getMessage()));

}

    UserDetails loadedUser= userService.loadUserByUsername(username);
    String generatedToken= jwtUtils.generateToken(loadedUser);
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    UserModel user = userRepository.findByUsername(username);
    String role = user.getRole();

     return ResponseEntity.ok(new AuthenticationResponse(generatedToken, role));

}


//Update Profile
@PutMapping("/updateprofile/{id}")
private ResponseEntity<?> authenticateClientUpdate(@PathVariable("id") String id,@RequestBody UserModel userModel){

Optional<UserModel> userOptional = userRepository.findById(id);

UserModel e=  userRepository.findByUsername(userOptional.get().getUsername());
String role= e.getRole();
if(role.equals("stagiaire"))
{
UserModel saveUser =userOptional.get();
saveUser.setUsername(userModel.getUsername()!=null ? userModel.getUsername() : saveUser.getUsername());
saveUser.setFullname(userModel.getFullname()!=null ? userModel.getFullname() : saveUser.getFullname());
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setAge(userModel.getAge()!= null ? userModel.getAge() : saveUser.getAge());
saveUser.setSpeciality(userModel.getSpeciality()!=null ? userModel.getSpeciality() : saveUser.getSpeciality());
userRepository.save(saveUser);
return new ResponseEntity<>("test", HttpStatus.OK);
}
else if (role.equals("employee")){
UserModel saveUser = userOptional.get();
saveUser.setUsername(userModel.getUsername()!=null ? userModel.getUsername() : saveUser.getUsername());
saveUser.setFullname(userModel.getFullname()!=null ? userModel.getFullname() : saveUser.getFullname());
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setAge(userModel.getAge()!= null ? userModel.getAge() : saveUser.getAge());
saveUser.setSpeciality(userModel.getSpeciality()!=null ? userModel.getSpeciality() : saveUser.getSpeciality());
saveUser.setDiploma(userModel.getDiploma()!=null ? userModel.getDiploma() : saveUser.getDiploma());
userRepository.save(saveUser);
return new ResponseEntity<>("DONE", HttpStatus.OK);
}
else if (role.equals("recruiteur")){
UserModel saveUser = userOptional.get();
saveUser.setUsername(userModel.getUsername()!=null ? userModel.getUsername() : saveUser.getUsername());
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setEntreprise_name(userModel.getEntreprise_name()!=null ? userModel.getEntreprise_name() : saveUser.getEntreprise_name());
saveUser.setEntreprise_domaine(userModel.getEntreprise_domaine()!=null ? userModel.getEntreprise_domaine() : saveUser.getEntreprise_domaine());
userRepository.save(saveUser);
return new ResponseEntity<>("DONE", HttpStatus.OK);
}
else if (role.equals("admin")){
UserModel saveUser = userOptional.get();
saveUser.setFullname(userModel.getFullname()!=null ? userModel.getFullname() : saveUser.getFullname());
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setUsername(userModel.getUsername()!=null ? userModel.getUsername() : saveUser.getUsername());
userRepository.save(saveUser);
return new ResponseEntity<>("DONE", HttpStatus.OK);
}
else {
return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);

}

}

@PutMapping("/editpicture/{id}")
private ResponseEntity<?> updatePicture(@PathVariable("id") String id,@RequestBody UserModel userModel){

Optional<UserModel> userOptional = userRepository.findById(id);

UserModel e=  userRepository.findByUsername(userOptional.get().getUsername());

try{
UserModel saveUser =userOptional.get();
saveUser.setUserPicture(userModel.getUserPicture()!=null ? userModel.getUserPicture() : saveUser.getUserPicture());
userRepository.save(saveUser);
return new ResponseEntity<>(HttpStatus.OK);
}catch(Exception ex)
{
return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

}
}

@PutMapping("/editlogoEntreprise/{id}")
private ResponseEntity<?> updateEntrepriseLogo(@PathVariable("id") String id,@RequestBody UserModel userModel){

Optional<UserModel> userOptional = userRepository.findById(id);

UserModel e=userRepository.findByUsername(userOptional.get().getUsername());

try{
UserModel saveUser =userOptional.get();
saveUser.setEntreprise_logo(userModel.getEntreprise_logo()!=null ? userModel.getEntreprise_logo() : saveUser.getEntreprise_logo());
userRepository.save(saveUser);
return new ResponseEntity<>(HttpStatus.OK);
}catch(Exception ex)
{
return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

}
}


@GetMapping("/get-user/{id_user}")
public ResponseEntity<?> getUser(@PathVariable String id_user) {
	if (userRepository.findById(id_user).isPresent()) {
		UserModel u = userRepository.findById(id_user).get();
		return new ResponseEntity<UserModel>(u, HttpStatus.OK);
	} else {
		return new ResponseEntity<>("Error finding user", HttpStatus.NOT_FOUND);
	}
}

}
