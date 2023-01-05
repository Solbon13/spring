package com.example.demo.dto.organization.request;

import javax.validation.constraints.NotBlank;

public class MenuSiteRequest {
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private Long org_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
}
