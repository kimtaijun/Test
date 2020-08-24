package com.cobra.proxy.model.kubesphere;

/**
 * @author cobra 调用KubeSphere创建新用户时数据对象
 *
 */
public class InviteUser {
	/** 用户名即是登录名 */
	private String username;
	/** 角色 */
	private String workspace_role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWorkspace_role() {
		return workspace_role;
	}

	public void setWorkspace_role(String workspace_role) {
		this.workspace_role = workspace_role;
	}
}
