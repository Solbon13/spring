package com.example.demo.dto.organization.request;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class PersonRequest {
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String email;
	private String password;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String middleName;

	private Long departament_id;

	private Long position_id;
	private Set<String> role;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
