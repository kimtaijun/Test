package com.cobra.proxy.model.iam;


/**
 * @author cobra
 * 代理服务和sso平台交互用户信息数据对象
 */
public class UserInfo {
	private String userApiKey;
	private String accountName;
	private String userId;

	public String getUserApiKey() {
		return userApiKey;
	}

	public void setUserApiKey(String userApiKey) {
		this.userApiKey = userApiKey;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserInfoResponse [userApiKey=" + userApiKey + ", accountName=" + accountName + ", userId=" + userId
				+ "]";
	}

}
