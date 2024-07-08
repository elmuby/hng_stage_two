package com.hngbackend.hng_stage_two.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hngbackend.hng_stage_two.model.UserOrganisation;

public interface UserOrganizationRepository extends JpaRepository<UserOrganisation, Long> {

}
