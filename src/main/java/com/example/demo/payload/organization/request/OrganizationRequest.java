package com.example.demo.payload.organization.request;

import javax.validation.constraints.NotBlank;

public class OrganizationRequest {
	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
