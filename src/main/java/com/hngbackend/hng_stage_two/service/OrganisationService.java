package com.hngbackend.hng_stage_two.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hngbackend.hng_stage_two.dto.OrganisationDTO;
import com.hngbackend.hng_stage_two.model.Organisation;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.repository.OrganizationRepository;

@Service
public class OrganisationService {
	private OrganizationRepository organisationRepository;

	public OrganisationService(OrganizationRepository organizationRepository) {
		super();
		this.organisationRepository = organizationRepository;

	}


	public List<OrganisationDTO> getOrganisationByUser(User user) {

		return organisationRepository.findByUser(user).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private OrganisationDTO convertToDTO(Organisation organisation) {
		OrganisationDTO organisationDTO = new OrganisationDTO();
		organisationDTO.setOrgId(organisation.getOrgId());
		organisationDTO.setName(organisation.getName());
		organisationDTO.setDescription(organisation.getDescription());
		return organisationDTO;
	}
	
	 public Optional<Organisation> findById(String orgId) {
	        return organisationRepository.findById(orgId);
	    }

}
