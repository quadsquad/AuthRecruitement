package com.auth.demo.controllers;

import java.util.Optional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.demo.entities.AuthenticationRequest;
import com.auth.demo.entities.AuthenticationResponse;
import com.auth.demo.entities.ConfirmationToken;
import com.auth.demo.entities.UserModel;
import com.auth.demo.repositories.ConfirmationTokenRepository;
import com.auth.demo.repositories.UserRepository;
import com.auth.demo.services.EmailSenderService;
import com.auth.demo.services.JwtUtils;
import com.auth.demo.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = {"https://myworldjob.herokuapp.com", "http://localhost:4200"})
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

@Autowired
private ConfirmationTokenRepository confirm;

@Autowired
private EmailSenderService emailSenderService;

List<Object> recruters = new ArrayList<Object>();

@GetMapping("/content")
private String testingToken(AuthenticationRequest authenticationrequest, UserModel usermodel, String authenticationResponse){

 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
     String email = loggedInUser.getName();
     UserModel user = userRepository.findByEmail(email);
     String role = user.getEmail();
     	
         return role;
}


@GetMapping("/getToken")
private ResponseEntity<?> getToken(AuthenticationRequest authenticationrequest, UserModel usermodel, String authenticationResponse){

	 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	     String email = loggedInUser.getName();
	     UserModel user = userRepository.findByEmail(email);
	    // String role = user.getRole();
	     	
	     return new ResponseEntity<>(email, HttpStatus.ACCEPTED);  	
       
}

@GetMapping("/findallrecruiters")
public ResponseEntity<?> getAllRecruters(){
	List<UserModel> users =	userRepository.findAll();
	if(!users .isEmpty()){
		for (int i = 0; i<users.size(); i++) {
			if (users.get(i).getRole().equals("Business")) {
				recruters.add(users.get(i));
			}
		}
		return new ResponseEntity<>(recruters , HttpStatus.OK);
		}
	else 
	{
			return new ResponseEntity<String>("No users  Available",HttpStatus.NOT_FOUND);
	}
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
     String email = loggedInUser.getName();
     UserModel user = userRepository.findByEmail(email);
 	String lastname = user.getLastname();
	String firstname = user.getFirstname();
	String userPicture = user.getUserPicture();
	String role = user.getRole();
	String phonenumber= user.getPhonenumber();
	String country= user.getCountry();
	String countryIso= user.getCountryIso();
	String city= user.getCity();
	String business_logo= user.getBusiness_logo();
    String address = user.getAddress();
    String business_website= user.getBusiness_website();
    String business_name= user.getBusiness_name();

     if (role.equals("particular")){
return new UserModel(email, role, userPicture, firstname, lastname, country, city);
}else{
return new UserModel(email, phonenumber, country, countryIso, city, business_name, business_logo, business_website, address, role);
}
}

//Subscribe
/**
 * @param authenticationRequest
 * @return
 */
/**
 * @param authenticationRequest
 * @return
 */
@PostMapping("/subscribe")
private ResponseEntity<?> subscribeClient(@RequestBody AuthenticationRequest authenticationRequest){
String pwd= authenticationRequest.getPassword();
String cpwd= authenticationRequest.getConfirmpassword();
String lastname = authenticationRequest.getLastname();
String firstname = authenticationRequest.getFirstname();
String userPicture = authenticationRequest.getUserPicture();
String role = authenticationRequest.getRole();
String phonenumber= authenticationRequest.getPhonenumber();
String country= authenticationRequest.getCountry();
String countryIso= authenticationRequest.getCountryIso();
String city= authenticationRequest.getCity();
String business_logo= authenticationRequest.getBusiness_logo();
String address = authenticationRequest.getAddress();
String business_website= authenticationRequest.getBusiness_website();
String business_name= authenticationRequest.getBusiness_name();
String email= authenticationRequest.getEmail();

UserModel userModel= new UserModel();
if(role.equals("Particular"))
{
userModel.setLastname(lastname);
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setFirstname(firstname);
userModel.setEmail(email);
userModel.setCountry(country);
userModel.setCity(city);
userModel.setUserPicture(userPicture);
userModel.setRole(role);
userModel.setEnabled(false);
} else if (role.equals("Business"))
{
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setEmail(email);
userModel.setBusiness_name(business_name);
userModel.setBusiness_logo(business_logo);
userModel.setBusiness_website(business_website);
userModel.setAddress(address);
userModel.setPhonenumber(phonenumber);
userModel.setCountry(country);
userModel.setCountryIso(countryIso);
userModel.setCity(city);
userModel.setRole(role);
userModel.setEnabled(false);

}else if (role.equals("admin")) {
	userModel.setLastname(lastname);
	userModel.setPassword(passwordencoder.encode(pwd));
	userModel.setFirstname(firstname);
	userModel.setEmail(email);
	userModel.setCountry(country);
	userModel.setCity(city);
	userModel.setUserPicture(userPicture);
	userModel.setRole(role);
}
else {
System.err.println();
}
ConfirmationToken confirmationToken = new ConfirmationToken(userModel);
try {
	
	System.out.println(confirmationToken.getConfirmationToken());
	System.out.println(userModel);
	confirm.save(confirmationToken);
} catch (Exception ex) {
	System.out.println("Error saving confirm token");
}

try {
userRepository.save(userModel);

SimpleMailMessage mailMessage = new SimpleMailMessage();
mailMessage.setTo(userModel.getEmail());
mailMessage.setSubject("Complete Registration!");
mailMessage.setFrom("quadsquad1997@gmail.com");
mailMessage.setText("To confirm your account, please click here : "
+"https://authrecruitement.herokuapp.com/confirm-account?token="+confirmationToken.getConfirmationToken());

emailSenderService.sendEmail(mailMessage);


}catch(Exception e){
return ResponseEntity.ok(new AuthenticationResponse("Error during subscription: "+email));

}
return ResponseEntity.ok(new AuthenticationResponse("Successful subscription"+email));

}

@GetMapping("/confirm-account")
public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken)
{
    ConfirmationToken token = confirm.findByConfirmationToken(confirmationToken);
    System.out.println("Your confirm token is "+token.getConfirmationToken());
    try {
    	UserModel user = userRepository.findByEmail(token.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        confirm.deleteByConfirmationToken(token.getConfirmationToken());
         return ResponseEntity.status(HttpStatus.FOUND)
      	        .location(URI.create("http://localhost:4200/verified"))
      	        .build();
    } catch (Exception ex) {
    	return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

@PostMapping("/auth")
private ResponseEntity<?> authenticateClient(@RequestBody AuthenticationRequest authenticationRequest){
String email= authenticationRequest.getEmail();
String password= authenticationRequest.getPassword();


try {
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

}catch(Exception e){
return ResponseEntity.ok(new AuthenticationResponse(e.getMessage()));

}

    UserDetails loadedUser= userService.loadUserByUsername(email);
    String generatedToken= jwtUtils.generateToken(loadedUser);
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    UserModel user = userRepository.findByEmail(email);
    String role = user.getRole();
     return ResponseEntity.ok(new AuthenticationResponse(generatedToken, role, user));

}

@PutMapping("/update-business-profile/{idB}")
public ResponseEntity<?> updateBusinessProfile(@RequestBody UserModel userModel, @PathVariable String idB) {
	try {
		Optional<UserModel> userB = userRepository.findById(idB);
		UserModel userBToSave = userB.get();
		userBToSave.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : userBToSave.getEmail());
		userBToSave.setBusiness_name(userModel.getBusiness_name()!=null ? userModel.getBusiness_name() : userBToSave.getBusiness_name());
		userBToSave.setAddress(userModel.getAddress()!=null ? userModel.getAddress() : userBToSave.getAddress());
		userBToSave.setCity(userModel.getCity()!=null ? userModel.getCity() : userBToSave.getCity());
		userBToSave.setPhonenumber(userModel.getPhonenumber()!=null ? userModel.getPhonenumber() : userBToSave.getPhonenumber());
		userBToSave.setCountry(userModel.getCountry()!=null ? userModel.getCountry() : userBToSave.getCountry());
		userBToSave.setBusiness_website(userModel.getBusiness_website()!=null ? userModel.getBusiness_website() : userBToSave.getBusiness_website());
		userRepository.save(userBToSave);
		return new ResponseEntity<UserModel>(userBToSave, HttpStatus.OK);
	} catch (Exception ex) {
		return new ResponseEntity<>("ERROR UPDATING BUSINESS PROFILE", HttpStatus.BAD_REQUEST);
	}
}


//Update Profile
@PutMapping("/updateprofileconnected")
private ResponseEntity<?> authenticateClientUpdate(@RequestBody UserModel userModel){

	 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
     String email = loggedInUser.getName();
     UserModel user = userRepository.findByEmail(email);
     Optional<UserModel> userOptional = userRepository.findById(user.getId());

UserModel e=  userRepository.findByEmail(userOptional.get().getEmail());
String role= e.getRole();
if(role.equals("particular"))
{
UserModel saveUser =userOptional.get();
saveUser.setFirstname(userModel.getFirstname()!=null ? userModel.getFirstname() : saveUser.getFirstname());
saveUser.setLastname(userModel.getLastname()!=null ? userModel.getLastname() : saveUser.getLastname());
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setCountry(userModel.getCountry()!=null ? userModel.getCountry() : saveUser.getCountry());
saveUser.setCity(userModel.getCity()!=null ? userModel.getCity() : saveUser.getCity());

userRepository.save(saveUser);
return new ResponseEntity<>("test", HttpStatus.OK);
}

else if (role.equals("business")){
UserModel saveUser = userOptional.get();
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setBusiness_name(userModel.getBusiness_name()!=null ? userModel.getBusiness_name() : saveUser.getBusiness_name());
saveUser.setBusiness_logo(userModel.getBusiness_logo()!=null ? userModel.getBusiness_logo() : saveUser.getBusiness_logo());
saveUser.setAddress(userModel.getAddress()!=null ? userModel.getAddress() : saveUser.getAddress());
saveUser.setCity(userModel.getCity()!=null ? userModel.getCity() : saveUser.getCity());
saveUser.setPhonenumber(userModel.getPhonenumber()!=null ? userModel.getPhonenumber() : saveUser.getPhonenumber());
saveUser.setCountry(userModel.getCountry()!=null ? userModel.getCountry() : saveUser.getCountry());
saveUser.setBusiness_website(userModel.getBusiness_website()!=null ? userModel.getBusiness_website() : saveUser.getBusiness_website());

userRepository.save(saveUser);
return new ResponseEntity<>("DONE", HttpStatus.OK);
}
/*else if (role.equals("admin")){
UserModel saveUser = userOptional.get();
saveUser.setFullname(userModel.getFullname()!=null ? userModel.getFullname() : saveUser.getFullname());
saveUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : saveUser.getEmail());
saveUser.setUsername(userModel.getUsername()!=null ? userModel.getUsername() : saveUser.getUsername());
userRepository.save(saveUser);
return new ResponseEntity<>("DONE", HttpStatus.OK);
}*/
else {
return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);

}

}

@PutMapping("/editpicture")
private ResponseEntity<?> updatePicture(@RequestBody UserModel userModel){

	 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
     String email = loggedInUser.getName();
     UserModel user = userRepository.findByEmail(email);
     Optional<UserModel> userOptional = userRepository.findById(user.getId());

UserModel e=userRepository.findByEmail(userOptional.get().getEmail());

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

@PutMapping("/editlogoEntreprise")
private ResponseEntity<?> updateEntrepriseLogo(@RequestBody UserModel userModel){

	 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
     String email = loggedInUser.getName();
     UserModel user = userRepository.findByEmail(email);
     Optional<UserModel> userOptional = userRepository.findById(user.getId());

UserModel e=userRepository.findByEmail(userOptional.get().getEmail());

try{
UserModel saveUser =userOptional.get();
saveUser.setBusiness_logo(userModel.getBusiness_logo()!=null ? userModel.getBusiness_logo() : saveUser.getBusiness_logo());
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
