package com.hngbackend.hng_stage_two.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Organisation {

	public Organisation() {
		super();
		this.orgId = UUID.randomUUID().toString();

	}

	public Organisation(String orgId, @NotNull(message = "Name is required") String name, String description
			) {
		super();
		this.orgId = orgId;
		this.name = name;
		this.description = description;
		
	}

	@Id
	private String orgId;

	@NotNull(message = "Name is required")
	@Column(nullable = false)
	private String name;

	private String description;

	

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
