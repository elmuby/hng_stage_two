package com.hngbackend.hng_stage_two.service;

import org.springframework.stereotype.Service;

import com.hngbackend.hng_stage_two.model.Organisation;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.repository.OrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserRepository;

@Service
public class OrganisationService {
	
	public OrganisationService(OrganizationRepository organizationRepository) {
		super();
		this.organisationRepository = organizationRepository;
		
		
	}
	
	private OrganizationRepository organisationRepository;
	
	
	
	private UserRepository userRepository;
	
	 public Organisation createOrganization(Organisation organization, String email) {
		 User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	        Organisation savedOrg = organisationRepository.save(organization);
	        
	        
//	        user and organization relationship
	        
	        
	        
	        return organisationRepository.save(savedOrg);
	        
	    }

}
