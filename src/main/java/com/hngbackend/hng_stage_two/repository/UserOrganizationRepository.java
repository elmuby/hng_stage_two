package com.hngbackend.hng_stage_two.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.model.UserOrganisation;

public interface UserOrganizationRepository extends JpaRepository<UserOrganisation, Long> {
	List<UserOrganisation> findByUser(User user);
	 boolean existsByOrganisation_OrgIdAndUser_UserId(String orgId, String userId);
	List<UserOrganisation> findByUser_UserId(String userId);
}
