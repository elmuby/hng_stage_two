package com.hngbackend.hng_stage_two.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hngbackend.hng_stage_two.model.Organisation;
import com.hngbackend.hng_stage_two.model.User;

public interface OrganizationRepository extends JpaRepository<Organisation, String>  {
	
	@Query("SELECT o FROM Organisation o JOIN o.userOrganisations uo WHERE uo.user = :user")
    List<Organisation> findByUser(@Param("user") User user);
}
