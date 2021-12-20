package com.auth.demo.entities;

public class AuthenticationResponse {
	private String response;
	private String r;
	private UserModel user;
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	

	public AuthenticationResponse(String response) {
		super();
		this.response = response;
	}

	public AuthenticationResponse(String response, String r) {
		super();
		this.response = response;
		this.r=r;
	}
	
	public AuthenticationResponse(String response, String r, UserModel user) {
		super();
		this.response = response;
		this.r=r;
		this.user=user;
	}

	public AuthenticationResponse() {
		super();
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

}
