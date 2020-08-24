package com.cobra.proxy.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @BelongsProject: iam-universal-sso-proxy
 * @BelongsPackage: com.cobra.proxy.properties
 * @Author: t480
 * @CreateTime: 2020-08-10 09:53
 * @Description: swagger config
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private boolean flag;
    private String title;
    private String description;
    private String version;
    private boolean showIam;
    private boolean showKubesphere;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isShowIam() {
        return showIam;
    }

    public void setShowIam(boolean showIam) {
        this.showIam = showIam;
    }

    public boolean isShowKubesphere() {
        return showKubesphere;
    }

    public void setShowKubesphere(boolean showKubesphere) {
        this.showKubesphere = showKubesphere;
    }
}
