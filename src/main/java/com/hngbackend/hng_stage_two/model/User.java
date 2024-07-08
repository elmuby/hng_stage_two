package com.hngbackend.hng_stage_two.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

	public User() {
		super();
		this.userId = UUID.randomUUID().toString();
		

	}

	public User(String userId, @NotNull(message = "first name is required") String firstName,
			@NotNull(message = "last name is required") String lastName,
			@NotNull(message = "email is required") @Email String email,
			@NotNull(message = "password is required") String password, String phone, USER_ROLE role) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		
		this.role = role;
	}

	@Id
	private String userId;

	@Column(nullable = false)
	@NotNull(message = "first name is required")
	private String firstName;

	@Column(nullable = false)
	@NotNull(message = "last name is required")
	private String lastName;

	@Column(nullable = false, unique = true)
	@NotNull(message = "email is required")
	@Email
	private String email;

	@Column(nullable = false)
	@NotNull(message = "password is required")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String phone;

	
	private USER_ROLE  role = USER_ROLE.ROLE_USER;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}

}
