package com.cobra.proxy.model.kubesphere;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: iam-universal-sso-proxy
 * @BelongsPackage: com.cobra.proxy.model.kubesphere
 * @Author: t480
 * @CreateTime: 2020-08-04 21:18
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListUsers {

    private List<Map<String,String>> items;
    private String total_count;

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, String>> items) {
        this.items = items;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }
}