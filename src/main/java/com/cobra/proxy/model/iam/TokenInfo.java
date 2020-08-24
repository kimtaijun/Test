package com.cobra.proxy.model.iam;

import java.io.Serializable;

/**
 * @author cobra
 * 代理服务和SSO平台交互获取的用户信息对象
 *
 */
public class TokenInfo implements Serializable {

	private static final long serialVersionUID = 6957362458627470331L;
	private String accessToken;
	private String expire;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	@Override
	public String toString() {
		return "TokenResponse [accessToken=" + accessToken + ", expire=" + expire + "]";
	}

	public TokenInfo(String accessToken, String expire) {
		super();
		this.accessToken = accessToken;
		this.expire = expire;
	}

	public TokenInfo() {
		super();
	}

}
