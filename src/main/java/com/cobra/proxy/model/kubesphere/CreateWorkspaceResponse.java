package com.cobra.proxy.model.kubesphere;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author cobra 调用KubeSphere创建新企业空间时数据对象
 *
 */
public class CreateWorkspaceResponse {

	private String apiVersion;

	private String kind;

	private Metadata metadata;

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

	@ApiModel("creatResponse")
	public class Metadata{
		private String creationTimestamp;
		private Integer generation;
		private String name;
		private String resourceVersion;
		private String selfLink;
		private String uid;

		public String getCreationTimestamp() {
			return creationTimestamp;
		}

		public void setCreationTimestamp(String creationTimestamp) {
			this.creationTimestamp = creationTimestamp;
		}

		public Integer getGeneration() {
			return generation;
		}

		public void setGeneration(Integer generation) {
			this.generation = generation;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getResourceVersion() {
			return resourceVersion;
		}

		public void setResourceVersion(String resourceVersion) {
			this.resourceVersion = resourceVersion;
		}

		public String getSelfLink() {
			return selfLink;
		}

		public void setSelfLink(String selfLink) {
			this.selfLink = selfLink;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}
	}
}
