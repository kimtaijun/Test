package com.cobra.proxy.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "proxy.cobra")
public class ProxyProperties {

	public IAMProperties iam = new IAMProperties();
	public KubeSphereProperties kubesphere = new KubeSphereProperties();

	public IAMProperties getIam() {
		return iam;
	}

	public void setIam(IAMProperties iam) {
		this.iam = iam;
	}

	public KubeSphereProperties getKubesphere() {
		return kubesphere;
	}

	public void setKubesphere(KubeSphereProperties kubesphere) {
		this.kubesphere = kubesphere;
	}

}
