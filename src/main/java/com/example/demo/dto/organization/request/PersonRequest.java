package com.example.demo.dto.organization.request;

import javax.validation.constraints.NotBlank;

public class PersonRequest {
	private Long id;
	@NotBlank
	private String fastName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String middleName;
	@NotBlank
	private Long departament_id;
	@NotBlank
	private Long position_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFastName() {
		return fastName;
	}

	public void setFastName(String fastName) {
		this.fastName = fastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Long getDepartament_id() {
		return departament_id;
	}

	public void setDepartament_id(Long departament_id) {
		this.departament_id = departament_id;
	}

	public Long getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Long position_id) {
		this.position_id = position_id;
	}
}
