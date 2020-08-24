package com.cobra.proxy.model.iam;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {
	private String code;
	private String data;
	private String message;

	public ResultResponse() {
	}

	public ResultResponse(String code, String data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public ResultResponse(String code, String data) {
		this.code = code;
		this.data = data;
	}

	public ResultResponse(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResultResponse [code=" + code + ", data=" + data + ", message=" + message + "]";
	}

}
