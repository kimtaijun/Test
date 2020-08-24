package com.cobra.proxy.model.kubesphere;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

/**
 * @author cobra 调用KubeSphere创建新企业空间时数据对象
 *
 */

public class CreateWorkspaceRequest {

	private String apiVersion;

	private String kind;

	private Metadata metadata = new Metadata();;

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@ApiModel("creatRequest")
	public class Metadata {
		//@NotBlank
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
