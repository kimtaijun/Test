package com.cobra.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cobra.proxy.properties.ProxyProperties;

@SpringBootTest
class IamGitlabSsoProxyApplicationTests {

	@Autowired
	private ProxyProperties proxyProperties;

	@Test
	void contextLoads() {
	}
}
