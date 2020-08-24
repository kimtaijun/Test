package com.cobra.proxy.util;

/**
 * @BelongsProject: springboot-k8
 * @BelongsPackage: com.bigdata.tools
 * @Author: t480
 * @CreateTime: 2020-07-24 14:50
 * @Description: workspace operate
 */
public enum WorkspaceOperate {
    CREATE,     //创建企业空间
    DELETE,     //删除企业空间
    SEARCH,     //查询企业空间
    INVITE_MEMBER,      //企业空间添加成员
    DELETE_MEMBER,      //企业空间删除成员
    SEARCH_MEMBER,      //企业空间查询成员
    SEARCH_ALL_MEMBER       //企业空间查询所有成员
}
