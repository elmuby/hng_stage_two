package com.hngbackend.hng_stage_two.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.response.ApiResponse;
import com.hngbackend.hng_stage_two.service.UserService;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;



	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//	@GetMapping("/{id}")
//	public ResponseEntity<Map<String, Object>> getUserDetailsById(@RequestHeader("Authorization") String authHeader,
//			@PathVariable("id") String userId, Principal principal) {
//
//		String requestingUserEmail = principal.getName();
//		logger.debug("Requesting user email: {}", requestingUserEmail);
//		
//		logger.debug("Requested user ID: {}", userId);
//
////		 Extract the requesting user's email from the JWT token
//		String token = authHeader.substring(7);
//
//		String userEmail = jwtUtil.getEmailFromJwtToken(token);
//
//		return userService.getUserDetailsForCurrentUserOrInOrganizations(userEmail, userId);
//
//	}
	@GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id, Principal principal, @RequestHeader("Authorization") String jwt) {
        String requestingUserEmail = principal.getName();
        String email = "";
        try {
        	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        logger.debug("Requesting user email: {}", email);
        logger.debug("Requested user ID: {}", id);

        User user = userService.getUserById(id);
        if (user != null) {
//            User user = userService.getUserById(id);
            logger.debug("User found: {}", user);
            return ResponseEntity.ok(new ApiResponse("success", "User fetched successfully", user));
        } else {
            logger.debug("Forbidden access attempt by user: {}", email);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("error", "Forbidden", null));
        }
    }
	
	@GetMapping("/user")
	public String hello () {
		return "hello from here";
	}
}
