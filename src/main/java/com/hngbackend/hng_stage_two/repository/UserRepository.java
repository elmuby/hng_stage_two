package com.hngbackend.hng_stage_two.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hngbackend.hng_stage_two.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
