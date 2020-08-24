package com.cobra.proxy.model.iam;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPushRequest {

	private String username;
	private String status;
	private boolean enabled;
	private String accountName;
	private List<String> roleCodes;
	private Mappingattr mappingAttr = new Mappingattr();
	private Extattr extAttr = new Extattr();
	private List<String> orgList;
	private String appCode;
	private String masterId;
	private List<String> appOrgList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public Mappingattr getMappingAttr() {
		return mappingAttr;
	}

	public void setMappingAttr(Mappingattr mappingAttr) {
		this.mappingAttr = mappingAttr;
	}

	public Extattr getExtAttr() {
		return extAttr;
	}

	public void setExtAttr(Extattr extAttr) {
		this.extAttr = extAttr;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public List<String> getAppOrgList() {
		return appOrgList;
	}

	public void setAppOrgList(List<String> appOrgList) {
		this.appOrgList = appOrgList;
	}

	@Override
	public String toString() {
		return "UserPushRequest [username=" + username + ", status=" + status + ", enabled=" + enabled
				+ ", accountName=" + accountName + ", roleCodes=" + roleCodes + ", mappingAttr=" + mappingAttr
				+ ", extAttr=" + extAttr + ", orgList=" + orgList + ", appCode=" + appCode + ", masterId=" + masterId
				+ ", appOrgList=" + appOrgList + "]";
	}

	public class Mappingattr {
		public String displayName;
		public String mobile;
		public String email;
		public String username;

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getMobile() {
			return mobile;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getEmail() {
			return email;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

		@Override
		public String toString() {
			return "Mappingattr [displayName=" + displayName + ", mobile=" + mobile + ", email=" + email + ", username="
					+ username + "]";
		}

	}

	public class Extattr {
		@JsonProperty("testAttr")
		public String testattr;

		public void setTestattr(String testattr) {
			this.testattr = testattr;
		}

		public String getTestattr() {
			return testattr;
		}

		@Override
		public String toString() {
			return "Extattr [testattr=" + testattr + "]";
		}

	}
}