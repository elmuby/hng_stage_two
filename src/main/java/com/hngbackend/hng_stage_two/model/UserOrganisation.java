package com.hngbackend.hng_stage_two.model;

import jakarta.persistence.*;

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
	private User user;

	@ManyToOne
	@JoinColumn(name = "orgId")
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
