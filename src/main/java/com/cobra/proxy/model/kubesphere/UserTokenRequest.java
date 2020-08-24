package com.cobra.proxy.model.kubesphere;

import javax.validation.constraints.NotBlank;


public class UserTokenRequest {
	@NotBlank(message = "username不能为空")
	private String username;

	@NotBlank(message = "password不能为空")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserTokenRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
