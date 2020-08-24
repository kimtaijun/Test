package com.cobra.proxy.ErrorHandle;

import org.springframework.web.client.RestClientException;

/**
 * @BelongsProject: iam-universal-sso-proxy
 * @BelongsPackage: com.cobra.proxy.ErrorHandle
 * @Author: t480
 * @CreateTime: 2020-08-04 21:05
 * @Description:
 */
public class CustomException extends RestClientException {

    private RestClientException restClientException;
    private String body;

    public RestClientException getRestClientException() {
        return restClientException;
    }

    public void setRestClientException(RestClientException restClientException) {
        this.restClientException = restClientException;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public CustomException(String msg, RestClientException restClientException, String body) {
        super(msg);
        this.restClientException = restClientException;
        this.body = body;
    }

}