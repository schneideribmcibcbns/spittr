package com.manning.sia.springmvc.spittr.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Spitter {

	private Long id;
	
	@NotNull
	@Size(min=5, max=16, message="{username.size}")
	private String username;
	
	@NotNull
	@Size(min=5, max=25, message="{password.size}")	
	private String password;
	
	@NotNull
	@Size(min=2, max=30, message="{firstName.size}")
	private String firstName;

	@NotNull
	@Size(min=2, max=30, message="{lastName.size}")
	private String lastName;
	
	@NotNull
	@Email(message="{email.valid}")
	private String email;
	
	private MultipartFile profilePicture;
	
	public Spitter() {
		
	}
	
	public Spitter(Long id, String username, String password, String firstName, String lastName, String email) {
	    this.id = id;
	    this.username = username;
	    this.password = password;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	    //this.updateByEmail = updateByEmail;
	}
	public Spitter(String username, String password, String firstName, String lastName, String email) {
		this(null, username, password, firstName, lastName, email);
	}

/*	public String toString() {
		return "{username: " + username + " password: " + password + " firstName: " + firstName + " lastName: " + lastName + " email: " + email + "}";
	}
*/}
