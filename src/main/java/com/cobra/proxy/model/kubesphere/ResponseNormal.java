package com.cobra.proxy.model.kubesphere;

import java.io.Serializable;

/**
 * @BelongsProject: iam-universal-sso-proxy
 * @BelongsPackage: com.cobra.proxy.model.kubesphere
 * @Author: t480
 * @CreateTime: 2020-08-05 15:32
 * @Description:
 */
public class ResponseNormal implements Serializable {
    private static final long serialVersionUID = 6957362458627470331L;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
