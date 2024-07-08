package com.hngbackend.hng_stage_two.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hngbackend.hng_stage_two.config.JwtProvider;
import com.hngbackend.hng_stage_two.dto.UserDto;
import com.hngbackend.hng_stage_two.model.Organisation;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.model.UserOrganisation;
import com.hngbackend.hng_stage_two.repository.OrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserOrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserRepository;
import com.hngbackend.hng_stage_two.request.LoginRequest;
import com.hngbackend.hng_stage_two.response.ApiErrorResponse;
import com.hngbackend.hng_stage_two.response.ApiResponse;
import com.hngbackend.hng_stage_two.response.AuthResponse;
import com.hngbackend.hng_stage_two.response.ErrorResponse;
import com.hngbackend.hng_stage_two.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	private JwtProvider jwtProvider;

	private CustomUserDetailsService customUserDetailsService;

	private OrganizationRepository organizationRepository;
	
	private UserOrganizationRepository userOrganizationRepository;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, 
			CustomUserDetailsService customUserDetailsService, OrganizationRepository organizationRepository,
			JwtProvider jwtProvider, UserOrganizationRepository userOrganizationRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customUserDetailsService = customUserDetailsService;
		this.organizationRepository = organizationRepository;
		this.jwtProvider = jwtProvider;
		this.userOrganizationRepository = userOrganizationRepository;
	}

	@PostMapping("register")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) throws Exception {
		if (userRepository.existsByEmail(user.getEmail())) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Bad Request", "Email is already taken"));
		}
		try {
			User userCreated = new User();
			userCreated.setEmail(user.getEmail());
			userCreated.setFirstName(user.getFirstName());
			userCreated.setLastName(user.getLastName());
			userCreated.setPassword(passwordEncoder.encode(user.getPassword()));
			userCreated.setPhone(user.getPhone());

			User savedUser = userRepository.save(userCreated);

//		to create default organization for user
			Organisation defaultOrganization = new Organisation();
			defaultOrganization.setName(savedUser.getFirstName() + "'s Organisation");
			defaultOrganization.setDescription("Default organization for " + user.getFirstName());
			organizationRepository.save(defaultOrganization);
			
//			to create user organization relationship
			UserOrganisation userOrganisation = new UserOrganisation();
			userOrganisation.setOrganisation(defaultOrganization);
			userOrganisation.setUser(savedUser);
			userOrganizationRepository.save(userOrganisation);

//			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
//					user.getPassword());
//			Authentication authentication = authenticate(savedUser.getEmail(), savedUser.getPassword());
//			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtProvider.generateToken(authentication);

			UserDto userDto = new UserDto(user);

			AuthResponse authResponse = new AuthResponse(token, userDto);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse("success", "Registration successful", authResponse));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(400)
					.body(new ApiErrorResponse("Bad Request", "Registration unsuccessful", 400));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> signin(@RequestBody @Validated LoginRequest request) {
		try {
			Authentication authentication = authenticate(request.getEmail(), request.getPassword());
			
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
			
//			String jwt = jwtUtils.generateToken(authentication);
			
			String jwt = jwtProvider.generateToken(authentication);
			User user = userRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			UserDto userDto = new UserDto(user);

			AuthResponse authResponse = new AuthResponse(jwt, userDto);
			System.out.println(role);

			return ResponseEntity.ok(new ApiResponse("success", "Login successful", authResponse));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiErrorResponse("Bad request", "Authentication failed", 401));
		}

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
