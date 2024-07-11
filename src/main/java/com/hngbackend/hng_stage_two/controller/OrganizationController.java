package com.hngbackend.hng_stage_two.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hngbackend.hng_stage_two.dto.OrganisationDTO;
import com.hngbackend.hng_stage_two.model.Organisation;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.model.UserOrganisation;
import com.hngbackend.hng_stage_two.repository.OrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserOrganizationRepository;
import com.hngbackend.hng_stage_two.request.UserIdRequest;
import com.hngbackend.hng_stage_two.response.ApiErrorResponse;
import com.hngbackend.hng_stage_two.response.ApiResponse;
import com.hngbackend.hng_stage_two.service.OrganisationService;
import com.hngbackend.hng_stage_two.service.UserService;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api/organisations")
public class OrganizationController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserOrganizationRepository userOrganisationRepository;

	@Autowired
	private OrganisationService organisationService;

	@Autowired
	private OrganizationRepository organisationRepository;

	@GetMapping
	public ResponseEntity<?> getAllOrganisationForUser(Authentication authentication) {
		String userEmail = authentication.getName();
		try {
			User user = userService.findUserByEmail(userEmail);
			if (user != null) {
				List<OrganisationDTO> organisations = organisationService.getOrganisationByUser(user);
				return ResponseEntity.ok(new ApiResponse("success", "Retrieved Successfully", organisations));
			}
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/{orgId}")
	public ResponseEntity<?> getOneOrganisationForUser(Authentication authentication) {
		String userEmail = authentication.getName();
		try {
			User user = userService.findUserByEmail(userEmail);
			if (user != null) {
				List<OrganisationDTO> organisations = organisationService.getOrganisationByUser(user);
				return ResponseEntity.ok(new ApiResponse("success", "Retrieved Successfully", organisations.get(0)));
			}
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping
	public ResponseEntity<?> createNewOrganisation(Authentication authentication,
			@Validated @RequestBody Organisation organisation) {
		String userEmail = authentication.getName();
		try {
			User user = userService.findUserByEmail(userEmail);
			if (user != null) {

				String orgName = organisation.getName();
				String orgDescription = organisation.getDescription();
				Organisation newOrganisation = new Organisation();
				newOrganisation.setName(orgName);
				newOrganisation.setDescription(orgDescription);
				organisationRepository.save(newOrganisation);

//				to create user organization relationship
				UserOrganisation userOrganisation = new UserOrganisation();
				userOrganisation.setOrganisation(newOrganisation);
				userOrganisation.setUser(user);
				userOrganisationRepository.save(userOrganisation);
				OrganisationDTO organisations = new OrganisationDTO(newOrganisation);
				return ResponseEntity
						.ok(new ApiResponse("success", "Organisation Created Successfully", organisations));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorResponse("Bad Request", "Client Error", 400));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorResponse("Bad Request", "Client Error", 400));
		}
	}

	@PostMapping("/{orgId}/users")	
	public ResponseEntity<?> addUserToOrganisation(Authentication authentication, @Validated @RequestBody UserIdRequest userIdRequest,
			@PathVariable String orgId) {
		String userEmail = authentication.getName();
		try {
			User requestingUser = userService.findUserByEmail(userEmail);	
			if (requestingUser != null) {
				User user = userService.getUserById(userIdRequest.getUserId());
				Optional<Organisation> organisation = organisationService.findById(orgId);
				if (organisation.isPresent()) {
					UserOrganisation userOrganisation = new UserOrganisation();
					userOrganisation.setUser(user);
					userOrganisation.setOrganisation(organisation.get());
					userOrganisationRepository.save(userOrganisation);
					return ResponseEntity.ok(new ApiResponse("success", "User added to Organisation Successfully"));
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ApiErrorResponse("Bad Request", "Client Error", 400));
				}
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorResponse("Bad Request", "Client Error", 400));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorResponse("Bad Request", "Client Error", 400));
			
		}

	}

}
