package com.hngbackend.hng_stage_two.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hngbackend.hng_stage_two.config.JwtProvider;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.model.UserOrganisation;
import com.hngbackend.hng_stage_two.repository.UserOrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	
	private UserOrganizationRepository userOrganizationRepository;

	private JwtProvider jwtProvider;

	public UserService(UserRepository userRepository, 
			UserOrganizationRepository userOrganizationRepository, JwtProvider jwtProvider) {
		super();
		
		this.userRepository = userRepository;
		this.userOrganizationRepository = userOrganizationRepository;

		this.jwtProvider = jwtProvider;
	}

	public User findUserByJwtToken(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return user;
	}

	
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		if (user == null) {
			throw new Exception("user not found");
		}

		return user;

	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
	
	 public List<UserOrganisation> getUserOrganisations(String userId) {
	        return userOrganizationRepository.findByUser_UserId(userId);
	    }



	public User getUserById(String userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Really not found"));
	}

}
