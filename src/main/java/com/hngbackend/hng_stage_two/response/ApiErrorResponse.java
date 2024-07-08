package com.hngbackend.hng_stage_two.response;

public class ApiErrorResponse {
	

	public ApiErrorResponse() {
			super();
			// TODO Auto-generated constructor stub
		}

	private String status;
	private String message;
	private int statusCode;

	public ApiErrorResponse(String status, String message, int statusCode) {
		this.status = status;
		this.message = message;
		this.statusCode = statusCode;
	}

	public ApiErrorResponse(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
