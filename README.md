# iam-universal-sso-proxy

## 简介

iam-universal-sso-proxy代理服务用户在kubesphere和IAM认证中心之前协调数据。完成用户在IAM认证中心的认证并操作kubesphere相关api。

gitlab认证方式采用oauth2_generic。


## 服务接口

### 供IAM调用

用户推送：POST    /api/pushUser 用户推送

用户查询：POST    /api/queryUser 账号查询

### 

### 供Kubesphere调用

账户相关接口：   

    POST      /api/create/user 创建用户
    DELETE    /api/delete/user删除用户
    ···
    
企业空间相关接口：     

    POST  /api/create/workspace 创建企业空间
    GET   /api/search/workspace 查询企业空间
    ···
    
## 接口文档

http://IP:PORT/swagger-ui.html
          
