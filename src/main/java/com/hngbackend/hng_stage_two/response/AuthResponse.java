 package com.hngbackend.hng_stage_two.response;

import com.hngbackend.hng_stage_two.dto.UserDto;

public class AuthResponse{
	
	private String accessToken;
	public AuthResponse() {
		super();
		
	}
	private UserDto user;
	
	public AuthResponse(String accessToken, UserDto user) {
		super();
		this.accessToken = accessToken;
		this.user = user;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}

}
