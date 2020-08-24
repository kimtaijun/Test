package com.cobra.proxy.model.kubesphere;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author cobra 调用KubeSphere创建新用户时数据对象
 *
 */
public class SearchUserResponse {
	/** 用户名即是登录名 */
	private String username;
	private String email;
	private String description;
	private String create_time;
	private String avatar_url;
	private String last_login_time;
	private String status;
	private String cluster_role;
	private String lang;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	private List<Map<String, Object>> cluster_rules;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCluster_role() {
		return cluster_role;
	}

	public void setCluster_role(String cluster_role) {
		this.cluster_role = cluster_role;
	}

	public List<Map<String, Object>> getCluster_rules() {
		return cluster_rules;
	}

	public void setCluster_rules(List<Map<String, Object>> cluster_rules) {
		this.cluster_rules = cluster_rules;
	}
}
