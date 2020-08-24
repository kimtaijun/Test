package com.cobra.proxy.properties;

/**
 * @BelongsProject: iam-universal-sso-proxy
 * @BelongsPackage: com.cobra.proxy.properties
 * @Author: t480
 * @CreateTime: 2020-08-03 14:54
 * @Description: kubeSphere properties
 */
public class KubeSphereProperties {

    private String host;
    private String root;
    private String passwd;
    private boolean authFlag;
    private Integer tokenType;
    private String fieldName_token;
    private String fieldName_username;
    private String fieldName_workspace;
    private String fieldName_member;
    private String defaultPassword;
    private String defaultClusterRole;
    private String tokenUrl;
    private String userManageUrl;
    private String createWorkSpaceUrl;
    private String deleteWorkSpaceUrl;
    private String searchWorkSpaceUrl;
    private String workSpaceMemberUrl;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean isAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(boolean authFlag) {
        this.authFlag = authFlag;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }

    public String getFieldName_token() {
        return fieldName_token;
    }

    public void setFieldName_token(String fieldName_token) {
        this.fieldName_token = fieldName_token;
    }

    public String getFieldName_username() {
        return fieldName_username;
    }

    public void setFieldName_username(String fieldName_username) {
        this.fieldName_username = fieldName_username;
    }

    public String getFieldName_workspace() {
        return fieldName_workspace;
    }

    public void setFieldName_workspace(String fieldName_workspace) {
        this.fieldName_workspace = fieldName_workspace;
    }

    public String getFieldName_member() {
        return fieldName_member;
    }

    public void setFieldName_member(String fieldName_member) {
        this.fieldName_member = fieldName_member;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getDefaultClusterRole() {
        return defaultClusterRole;
    }

    public void setDefaultClusterRole(String defaultClusterRole) {
        this.defaultClusterRole = defaultClusterRole;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getUserManageUrl() {
        return userManageUrl;
    }

    public void setUserManageUrl(String userManageUrl) {
        this.userManageUrl = userManageUrl;
    }

    public String getCreateWorkSpaceUrl() {
        return createWorkSpaceUrl;
    }

    public void setCreateWorkSpaceUrl(String createWorkSpaceUrl) {
        this.createWorkSpaceUrl = createWorkSpaceUrl;
    }

    public String getDeleteWorkSpaceUrl() {
        return deleteWorkSpaceUrl;
    }

    public void setDeleteWorkSpaceUrl(String deleteWorkSpaceUrl) {
        this.deleteWorkSpaceUrl = deleteWorkSpaceUrl;
    }

    public String getSearchWorkSpaceUrl() {
        return searchWorkSpaceUrl;
    }

    public void setSearchWorkSpaceUrl(String searchWorkSpaceUrl) {
        this.searchWorkSpaceUrl = searchWorkSpaceUrl;
    }

    public String getWorkSpaceMemberUrl() {
        return workSpaceMemberUrl;
    }

    public void setWorkSpaceMemberUrl(String workSpaceMemberUrl) {
        this.workSpaceMemberUrl = workSpaceMemberUrl;
    }
}
