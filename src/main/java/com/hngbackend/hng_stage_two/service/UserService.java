package com.hngbackend.hng_stage_two.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hngbackend.hng_stage_two.config.JwtProvider;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.repository.OrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private OrganizationRepository organisationRepository;
	private JwtProvider jwtProvider;

	public UserService(UserRepository userRepository, OrganizationRepository organisationRepository,
			JwtProvider jwtProvider) {
		super();
		this.organisationRepository = organisationRepository;
		this.userRepository = userRepository;
		
		this.jwtProvider =  jwtProvider;
	}

	public User findUserByJwtToken(String jwt) throws Exception {
//		String email = jwtUtil.getEmailFromJwtToken(jwt);
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

	public ResponseEntity<Map<String, Object>> getUserDetailsForCurrentUserOrInOrganizations(String requestingUserEmail,
			String userId) {

//		to check if user has permission
//		boolean hasPermission = checkUserPermission(requestingUserEmail, userId);
//
//		if (hasPermission) {
////			
		Optional<User> optionalUser = userRepository.findById(userId);
//			if (optionalUser.isPresent()) {
//
		User user = optionalUser.get();

		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("message", "User details retrieved successfully");

		Map<String, Object> data = new HashMap<>();
		data.put("userId", user.getUserId());
		data.put("firstName", user.getFirstName());
		data.put("lastName", user.getLastName());
		data.put("email", user.getEmail());
		data.put("phone", user.getPhone());
		response.put("data", data);
		return ResponseEntity.ok(response);
//
//			} else {
//				Map<String, Object> response = new HashMap<>();
//				response.put("status", "error");
//				response.put("message", "User not found");
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//			}
//		} else {
//			Map<String, Object> response = new HashMap<>();
//			response.put("status", "error");
//			response.put("message", "You do not have permission to access this user's details");
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
//		}
//
//	}

//	private boolean checkUserPermission(String requestingUserEmail, String userId) {
//		// Fetch organisations where the requesting user is a member or creator
//		List<Organisation> organisations = organisationRepository.findByUserEmail(requestingUserEmail);
//
//		// Check if the user with userId belongs to any of these organisations
//		for (Organisation org : organisations) {
//			if (org.getUsers().stream().anyMatch(user -> user.getUserId().equals(userId))) {
//				return true;
//			}
//		}
//		return false;
//	}
//	public boolean checkUserPermission(String requestingUserEmail, String userId) {
//		List<Organisation> organisations = organisationRepository.findByUserEmail(requestingUserEmail);
//		for (Organisation org : organisations) {
//			if (org.getUsers().stream().anyMatch(user -> user.getUserId().equals(userId))) {
//				return true;
//			}
//		}
//		return false;
	}

	public User getUserById(String userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}

}
