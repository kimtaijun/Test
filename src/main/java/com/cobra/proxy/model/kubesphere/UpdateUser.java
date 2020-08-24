package com.cobra.proxy.model.kubesphere;

/**
 * @author cobra 调用KubeSphere创建新用户时数据对象
 *
 */
public class UpdateUser {
	/** 用户名即是登录名 */
	private String username;
	/** 密码 */
	private String password;
	/** 当前密码 */
	private String current_password;
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

	public String getCurrent_password() {
		return current_password;
	}

	public void setCurrent_password(String current_password) {
		this.current_password = current_password;
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

	public UpdateUser(String username, String password, String email, String cluster_role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.cluster_role = cluster_role;
	}
}
