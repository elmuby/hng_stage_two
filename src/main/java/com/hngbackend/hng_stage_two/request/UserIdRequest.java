package com.hngbackend.hng_stage_two.request;

import jakarta.validation.constraints.NotNull;

public class UserIdRequest {
	@NotNull(message = "User ID is required")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
