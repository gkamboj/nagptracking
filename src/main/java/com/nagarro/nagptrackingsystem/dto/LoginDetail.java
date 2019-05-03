package com.nagarro.nagptrackingsystem.dto;

public class LoginDetail {

	private String email;
	private String password;

	public LoginDetail() {
	}

	public LoginDetail(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
