package com.hngbackend.hng_stage_two;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hngbackend.hng_stage_two.config.JwtProvider;
import com.hngbackend.hng_stage_two.config.JwtTokenValidator;
import com.hngbackend.hng_stage_two.controller.AuthController;
import com.hngbackend.hng_stage_two.model.User;
import com.hngbackend.hng_stage_two.repository.OrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserOrganizationRepository;
import com.hngbackend.hng_stage_two.repository.UserRepository;
import com.hngbackend.hng_stage_two.service.CustomUserDetailsService;

@WebMvcTest(AuthController.class)
public class AuthSpec {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private JwtProvider jwtProvider;

	@MockBean
	private JwtTokenValidator jwtTokenValidator;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private UserOrganizationRepository userOrganizationRepository;

	@MockBean
	private OrganizationRepository organizationRepository;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(userRepository, passwordEncoder,
				customUserDetailsService, organizationRepository, jwtProvider, userOrganizationRepository)).build();
	}
	
	@Test
	public void testUserRegistration()throws Exception{
		
		User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setPhone("1234567890");
        
//        Mock behaviour registry
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
//       Mock password encoder
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        

        // Mock JWT generation
        String jwtToken = "mocked_jwt_token";
        
        when(jwtProvider.generateToken(any(Authentication.class))).thenReturn(jwtToken);
        
	}

}
