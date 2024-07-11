package com.hngbackend.hng_stage_two.dto;

import com.hngbackend.hng_stage_two.model.Organisation;

public class OrganisationDTO {

	public OrganisationDTO() {
		super();
		
	}
	
	public OrganisationDTO(Organisation organisation) {
		super();
		this.orgId = organisation.getOrgId();
		this.name = organisation.getName();
		this.description = organisation.getDescription();
	}


	public OrganisationDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}




	private String orgId;
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
