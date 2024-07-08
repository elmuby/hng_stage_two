package com.hngbackend.hng_stage_two.response;

public class ApiResponse {
	
	    public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
		private String status;
	    private String message;
	    private Object data;
	    
	    public ApiResponse(String status, String message, Object data) {
	        this.status = status;
	        this.message = message;
	        this.data = data;
	    }
	    
	    public ApiResponse(String status, String message) {
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

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	    
		
}
