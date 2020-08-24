package com.cobra.proxy.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ProxyProperties.class)
public class ProxyConfig {

}
