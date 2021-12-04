package com.auth.demo.controllers;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

@Autowired
private ConfirmationTokenRepository confirm;

@Autowired
private EmailSenderService emailSenderService;

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
	String city= user.getCity();
	String business_logo= user.getBusiness_logo();
    String address = user.getAddress();
    String business_website= user.getBusiness_website();
    String business_name= user.getBusiness_name();

     if (role.equals("particular")){
return new UserModel(email, role, userPicture, firstname, lastname, country, city);
}else{
return new UserModel(email, phonenumber, country, city, business_name, business_logo, business_website, address, role);
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
String city= authenticationRequest.getCity();
String business_logo= authenticationRequest.getBusiness_logo();
String address = authenticationRequest.getAddress();
String business_website= authenticationRequest.getBusiness_website();
String business_name= authenticationRequest.getBusiness_name();
String email= authenticationRequest.getEmail();

UserModel userModel= new UserModel();
if(role.equals("particular"))
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
} else if (role.equals("business"))
{
userModel.setPassword(passwordencoder.encode(pwd));
userModel.setEmail(email);
userModel.setBusiness_name(business_name);
userModel.setBusiness_logo(business_logo);
userModel.setBusiness_website(business_website);
userModel.setAddress(address);
userModel.setPhonenumber(phonenumber);
userModel.setCountry(country);
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

try {
userRepository.save(userModel);
ConfirmationToken confirmationToken = new ConfirmationToken(userModel);

confirm.save(confirmationToken);

SimpleMailMessage mailMessage = new SimpleMailMessage();
mailMessage.setTo(userModel.getEmail());
mailMessage.setSubject("Complete Registration!");
mailMessage.setFrom("quadsquad1997@gmail.com");
mailMessage.setText("To confirm your account, please click here : "
+"http://localhost:8088/confirm-account?token="+confirmationToken.getConfirmationToken());

emailSenderService.sendEmail(mailMessage);


}catch(Exception e){
return ResponseEntity.ok(new AuthenticationResponse("Error during subscription"+email));

}
return ResponseEntity.ok(new AuthenticationResponse("Successful subscription"+email));

}

@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken)
{
    ConfirmationToken token = confirm.findByConfirmationToken(confirmationToken);

    if(token != null)
    {
        UserModel user = userRepository.findByEmail(token.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok(new AuthenticationResponse(" Account verified!"));
    }
    else
    {
       return  ResponseEntity.ok(new AuthenticationResponse(" The link is invalid or broken!"));
        
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
     return ResponseEntity.ok(new AuthenticationResponse(generatedToken, role));

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

//FN DE AHMED
@GetMapping("/get-user/{id_user}")
public ResponseEntity<?> getUser(@PathVariable String id_user) {
	if (userRepository.findById(id_user).isPresent()) {
		UserModel u = userRepository.findById(id_user).get();
		return new ResponseEntity<UserModel>(u, HttpStatus.OK);
	} else {
		return new ResponseEntity<>("Error finding user", HttpStatus.NOT_FOUND);
	}
}


/*
@RequestMapping(value="/register", method = RequestMethod.POST)
public void registerUser( UserModel user)
{

    User existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
    if(existingUser != null)
    {
        modelAndView.addObject("message","This email already exists!");
        modelAndView.setViewName("error");
    }
    else
    {
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailId());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("chand312902@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        modelAndView.addObject("emailId", user.getEmailId());

        modelAndView.setViewName("successfulRegisteration");
    }

    return modelAndView;
}*/


}
