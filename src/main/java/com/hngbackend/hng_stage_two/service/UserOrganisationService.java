package com.hngbackend.hng_stage_two.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hngbackend.hng_stage_two.model.UserOrganisation;
import com.hngbackend.hng_stage_two.repository.UserOrganizationRepository;

@Service
public class UserOrganisationService {
	
	@Autowired
    private UserOrganizationRepository userOrganisationRepository;
	
	public List<UserOrganisation> findByUserId(String userId) {
        return userOrganisationRepository.findByUser_UserId(userId);
    }
	
	public boolean existsByOrganisationIdAndUserId(String orgId, String userId) {
        return userOrganisationRepository.existsByOrganisation_OrgIdAndUser_UserId(orgId, userId);
    }

}
