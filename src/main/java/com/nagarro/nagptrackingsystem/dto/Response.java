package com.nagarro.nagptrackingsystem.dto;

public class Response {
	public Object data;
	public String status;

	public Response(Object data, String success)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.data = data;
		this.status = success;
	}

	public Object getData() {
		return data;
	}

	public String getStatus() {
		return status;
	}

}
