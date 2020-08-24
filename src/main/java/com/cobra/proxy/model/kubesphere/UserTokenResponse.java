package com.cobra.proxy.model.kubesphere;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class UserTokenResponse implements Serializable {
	private static final long serialVersionUID = 6957362458627470331L;
	private String access_token;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
