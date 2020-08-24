package com.cobra.proxy.model.kubesphere;

import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;

/**
 * @author cobra 调用KubeSphere创建新企业空间时数据对象
 *
 */
public class DeleteWorkspaceResponse {

	private String apiVersion;
	private String kind;
	private Metadata metadata;
	private Map<Object,Object> spec;
	private Map<Object,Object> status;


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

	public Map<Object, Object> getSpec() {
		return spec;
	}

	public void setSpec(Map<Object, Object> spec) {
		this.spec = spec;
	}

	public Map<Object, Object> getStatus() {
		return status;
	}

	public void setStatus(Map<Object, Object> status) {
		this.status = status;
	}

	@ApiModel("deleteResponse")
	public class Metadata{
		private String creationTimestamp;
		private List<String> finalizers;
		private Integer deletionGracePeriodSeconds;
		private Integer generation;
		private String deletionTimestamp;
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

		public List<String> getFinalizers() {
			return finalizers;
		}

		public void setFinalizers(List<String> finalizers) {
			this.finalizers = finalizers;
		}

		public Integer getDeletionGracePeriodSeconds() {
			return deletionGracePeriodSeconds;
		}

		public void setDeletionGracePeriodSeconds(Integer deletionGracePeriodSeconds) {
			this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
		}

		public Integer getGeneration() {
			return generation;
		}

		public void setGeneration(Integer generation) {
			this.generation = generation;
		}

		public String getDeletionTimestamp() {
			return deletionTimestamp;
		}

		public void setDeletionTimestamp(String deletionTimestamp) {
			this.deletionTimestamp = deletionTimestamp;
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
