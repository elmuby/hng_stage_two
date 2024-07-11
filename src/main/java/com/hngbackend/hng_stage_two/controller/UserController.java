package com.hngbackend.hng_stage_two.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hngbackend.hng_stage_two.dto.UserDto;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.model.UserOrganisation;
import com.hngbackend.hng_stage_two.response.ApiResponse;
import com.hngbackend.hng_stage_two.service.UserOrganisationService;
import com.hngbackend.hng_stage_two.service.UserService;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserOrganisationService userOrganisationService;

	

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable String id, Authentication authentication) {


		String userEmail = authentication.getName();
		User loggedInUser = userService.getUserByEmail(userEmail)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Check if the logged-in user is trying to access their own record
		if (loggedInUser.getUserId().equals(id)) {

			return ResponseEntity.ok(buildUserResponse(loggedInUser));
		}

		// Check if the logged-in user is trying to access records of users in their organizations
		List<UserOrganisation> userOrganisations = userOrganisationService.findByUserId(loggedInUser.getUserId());
		for (UserOrganisation userOrg : userOrganisations) {
			if (userOrganisationService.existsByOrganisationIdAndUserId(userOrg.getOrganisation().getOrgId(), id)) {
				User user = userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
				return ResponseEntity.ok(buildUserResponse(user));

			}
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("error", "Forbidden", null));
	}


	private ApiResponse buildUserResponse(User user) {
		UserDto userData = new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(),user.getEmail(), user.getPhone());
				return new ApiResponse("success", "User record found", userData);
	}
}
