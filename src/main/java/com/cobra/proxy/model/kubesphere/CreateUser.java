package com.cobra.proxy.model.kubesphere;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author cobra 调用KubeSphere创建新用户时数据对象
 *
 */
public class CreateUser{
	/** 用户名即是登录名 */
	private String username;
	/** 密码 */
	private String password;
	/** 邮箱 */
	private String email;
	/** 角色 */
	private String cluster_role;
	/** 角色 */
	private String description;
	/** 角色 */
	private String lang;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCluster_role() {
		return cluster_role;
	}

	public void setCluster_role(String cluster_role) {
		this.cluster_role = cluster_role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public String toString() {
		return "KubeSphereNewUser{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", cluster_role='" + cluster_role + '\'' +
				", description='" + description + '\'' +
				", lang='" + lang + '\'' +
				'}';
	}

	public CreateUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public CreateUser(String username, String password, String email, String cluster_role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.cluster_role = cluster_role;
	}

	public CreateUser(String username, String password, String email, String cluster_role, String description, String lang) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.cluster_role = cluster_role;
		this.description = description;
		this.lang = lang;
	}

	public CreateUser() {
	}
}
