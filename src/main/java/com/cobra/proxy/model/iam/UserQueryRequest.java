package com.cobra.proxy.model.iam;

import javax.validation.constraints.NotBlank;


public class UserQueryRequest {
	@NotBlank
	private String accountName;
	private String sync;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}
}
