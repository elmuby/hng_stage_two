package com.hngbackend.hng_stage_two.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserOrganisation {

	public UserOrganisation() {
		super();

	}

	public UserOrganisation(Long id, User user, Organisation organisation) {
		super();
		this.id = id;
		this.user = user;
		this.organisation = organisation;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;

	@ManyToOne
	@JoinColumn(name = "orgId")
	@JsonIgnore
	private Organisation organisation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

}
