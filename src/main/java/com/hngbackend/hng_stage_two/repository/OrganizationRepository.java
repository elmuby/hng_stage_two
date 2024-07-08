package com.hngbackend.hng_stage_two.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hngbackend.hng_stage_two.model.Organisation;

public interface OrganizationRepository extends JpaRepository<Organisation, String>  {

}
