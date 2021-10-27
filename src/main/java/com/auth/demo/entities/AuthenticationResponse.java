package com.auth.demo.entities;

public class AuthenticationResponse {
	private String response;
	private String r;
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

	public AuthenticationResponse() {
		super();
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

}
