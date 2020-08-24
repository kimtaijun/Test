package com.cobra.proxy.util;


/**
 * @BelongsProject: springboot-k8
 * @BelongsPackage: com.bigdata.tools
 * @Author: t480
 * @CreateTime: 2020-07-24 09:37
 * @Description: 常量定义
 */
public class ConstentDef {

    //kubesphere交互时获取token的方式，与kubesphere.properties相对应
    public static int TOKEN_FROM_URL = 1;
    public static int TOKEN_FROM_HEADER = 2;

    //kubesphere交互时获取token的方式，与kubesphere.properties相对应
    public static int QUERY_REQUEST = 1;
    public static int PUSH_REQUEST = 2;

    public static String CREATE_ERR_MSG = "调用api创建用户失败";
    public static String CREATE_EXCEPTION_MSG = "调用api创建用户出现异常";
    public static String DELETE_ERR_MSG = "调用api删除用户失败";
    public static String DELETE_EXCEPTION_MSG = "调用api删除用户出现异常";
    public static String OPERATE_EXCEPTION_MSG = "调用api无法操作管理员用户";
    public static String CREATE_WARN_MSG = "账号已存在";

    public static String DEFAULT_PASSWD = "FFFFFFFFFFFFFFF";
}
