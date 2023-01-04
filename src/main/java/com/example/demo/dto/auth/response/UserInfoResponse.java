package com.example.demo.dto.auth.response;

import com.example.demo.model.auth.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public UserInfoResponse(Long id, String username, String email, List<String> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public UserInfoResponse(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		List<String> roles = user.getRoles().stream()
				.map(item -> item.getName().toString())
				.collect(Collectors.toList());
		this.roles = roles;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}
