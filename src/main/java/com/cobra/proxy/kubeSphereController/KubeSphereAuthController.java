package com.cobra.proxy.kubeSphereController;

import com.cobra.proxy.model.kubesphere.*;
import com.cobra.proxy.properties.ProxyProperties;
import com.cobra.proxy.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @BelongsProject: springboot-k8
 * @BelongsPackage: com.bigdata.controller
 * @Author: t480
 * @CreateTime: 2020-07-22 10:41
 * @Description: 向IAM发起认证请求，并获取用户信息
 */

@RestController
@RequestMapping("/api")
public class KubeSphereAuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProxyProperties proxyProperties;

    public static RestTemplate restTemplate = new RestTemplate();

    /**
     * 根据用户名及密码获取kubesphere的access_token
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取token", httpMethod = "POST")
    @PostMapping("/getToken")
    public UserTokenResponse getKubeSphereToken(HttpServletRequest request, @RequestBody @Valid UserTokenRequest userTokenRequest) throws Exception {
        //  如果携带的密码为15位的F，则认为已经认证，使用默认密码获取token
        if(userTokenRequest.getPassword().equals(ConstentDef.DEFAULT_PASSWD)){
            userTokenRequest.setPassword(proxyProperties.kubesphere.getDefaultPassword());
        }

        ResponseEntity<Object> tokenResponse = (ResponseEntity<Object>) getAuthToken(userTokenRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        UserTokenResponse userTokenResponse = null;
        try {
            userTokenResponse = objectMapper.readValue(tokenResponse.getBody().toString(), UserTokenResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return userTokenResponse;

    }

/*
    @ApiOperation(value = "账号查询", httpMethod = "POST")
    @PostMapping("/queryUser")
    public ResultResponse queryUser(@RequestBody @Valid UserQueryRequest userQueryRequest) {
        try {
            String accountName = userQueryRequest.getAccountName();
            ResponseEntity<Object> tokenResponse = (ResponseEntity<Object>) getAuthToken(new UserTokenRequest(proxyProperties.getKubesphere().getRoot(),
                    proxyProperties.getKubesphere().getPasswd()));

            ObjectMapper objectMapper = new ObjectMapper();
            UserTokenResponse userTokenResponse = null;
            try {
                userTokenResponse = objectMapper.readValue(tokenResponse.getBody().toString(), UserTokenResponse.class);
            } catch (JsonProcessingException e) {
                logger.error("queryUser accessToken get failed!");
                e.printStackTrace();
            }
            String privateToken = userTokenResponse.getAccess_token();

            if (privateToken == null) {
                logger.error("queryUser accessToken get failed!");
                return null;
            }

            try {
                ResponseEntity<Object> response = (ResponseEntity<Object>) userOperateExcute(privateToken, accountName, Operate.SEARCH, null);
                if (response.getStatusCode().value() == 200) {
                    return new ResultResponse("SUCCESS", "true");
                } else {
                    return new ResultResponse("SUCCESS", "false");
                }
            } catch (Exception e) {
                logger.info("user " + accountName + " is not exist!");
                return new ResultResponse("SUCCESS", "false");
            }

        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("query err ! " + e.getMessage());
            return new ResultResponse("SUCCESS", "false");
        }

    }

    @ApiOperation(value = "用户推送", httpMethod = "POST")
    @PostMapping("/pushUser")
    public ResultResponse pushUser(@RequestBody UserPushRequest userPushRequest){
        try {
            logger.info("pushUser requset : " + userPushRequest);
            ResponseEntity<Object> tokenResponse = (ResponseEntity<Object>) getAuthToken(new UserTokenRequest(proxyProperties.getKubesphere().getRoot(),
                    proxyProperties.getKubesphere().getPasswd()));

            ObjectMapper objectMapper = new ObjectMapper();
            UserTokenResponse userTokenResponse = null;
            try {
                userTokenResponse = objectMapper.readValue(tokenResponse.getBody().toString(), UserTokenResponse.class);
            } catch (JsonProcessingException e) {
                logger.error("pushUser accessToken get failed!");
                e.printStackTrace();
            }

            String privateToken = userTokenResponse.getAccess_token();
            String accountName = userPushRequest.getAccountName();
            String status = userPushRequest.getStatus();

            if(proxyProperties.getKubesphere().getRoot().equals(accountName)){
                return new ResultResponse("ERROR", "",ConstentDef.OPERATE_EXCEPTION_MSG);
            }

            // 更新和新增，这里只处理新增
            if ("UPSERT".equals(status)) {
                try {
                    CreateUser createUser = new CreateUser(userPushRequest.getAccountName(),
                            proxyProperties.getKubesphere().getDefaultPassword(), userPushRequest.getMappingAttr().getEmail());
                    ResponseEntity<String> resBody = (ResponseEntity<String>) userOperateExcute(privateToken, accountName, Operate.CREATE, createUser);
                    if (resBody.getStatusCode().value() == 200) {
                        return new ResultResponse("SUCCESS");
                    } else {
                        return new ResultResponse("ERROR", "", ConstentDef.CREATE_ERR_MSG);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage());
                    return new ResultResponse("ERROR", "",e.getMessage());
                }

            } else if ("DELETE".equals(status)) {
                try {
                    ResponseEntity<String> resBody = (ResponseEntity<String>) userOperateExcute(privateToken, accountName, Operate.DELETE, null);
                    if (resBody.getStatusCode().value() == 200) {
                        return new ResultResponse("SUCCESS");
                    } else {
                        return new ResultResponse("ERROR", "",ConstentDef.DELETE_ERR_MSG);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage());
                    return new ResultResponse("ERROR", "",e.getMessage());
                }
            }

            return new ResultResponse("SUCCESS");
        }catch (Exception e) {
            //e.printStackTrace();
            logger.error("query err ! " + e.getMessage());
            return new ResultResponse("ERROR", "false", e.getMessage());
        }

    }
*/

    /***
     * Create a user account.
     * @param request
     * @return
     */
    @ApiOperation(value = "创建用户", httpMethod = "POST")
    @ApiImplicitParams(@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true))
    @PostMapping("/create/user")
    public OperateUserResponse createUser(HttpServletRequest request, @RequestBody @Valid CreateUser createUser) {
        //return userOperate(request, Operate.CREATE, kubeSphereNewUser);
        ResponseEntity<Object> response = (ResponseEntity<Object>) userOperate(request, Operate.CREATE, createUser);

        ObjectMapper objectMapper = new ObjectMapper();
        OperateUserResponse operateUserResponse = null;
        try {
            operateUserResponse = objectMapper.readValue(response.getBody().toString(), OperateUserResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return operateUserResponse;
    }

    /***
     * Describe the specified user.
     * @param request
     * @return
     */
    @ApiOperation(value = "查询用户", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "username", name = "username", dataType = "String", paramType = "header", required = true)})
    @GetMapping("/search/user")
    public SearchUserResponse searchUser(HttpServletRequest request) {

        ResponseEntity<Object> response = (ResponseEntity<Object>) userOperate(request, Operate.SEARCH, null);

        ObjectMapper objectMapper = new ObjectMapper();
        SearchUserResponse searchUserResponse = null;
        try {
            searchUserResponse = objectMapper.readValue(response.getBody().toString(), SearchUserResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return searchUserResponse;
    }

    /***
     * Delete the specified user.
     * @param request
     * @return
     */
    @ApiOperation(value = "删除用户", httpMethod = "DELETE")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "username", name = "username", dataType = "String", paramType = "header", required = true)})
    @DeleteMapping("/delete/user")
    public ResponseNormal deleteUser(HttpServletRequest request) {
        ResponseEntity<Object> response = (ResponseEntity<Object>) userOperate(request, Operate.DELETE, null);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNormal responseNormal = null;
        try {
            responseNormal = objectMapper.readValue(response.getBody().toString(), ResponseNormal.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return responseNormal;
    }

    /***
     * Update information about the specified user.
     * @param request
     * @return
     */
    @ApiOperation(value = "更新用户", httpMethod = "PUT")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "username", name = "username", dataType = "String", paramType = "header", required = true)})
    @PutMapping("/update/user")
    public OperateUserResponse updateUser(HttpServletRequest request, @RequestBody @Valid UpdateUser updateUser) {
        ResponseEntity<Object> response = (ResponseEntity<Object>) userOperate(request, Operate.UPDATE, updateUser);

        ObjectMapper objectMapper = new ObjectMapper();
        OperateUserResponse operateUserResponse = null;
        try {
            operateUserResponse = objectMapper.readValue(response.getBody().toString(), OperateUserResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return operateUserResponse;
    }

    /***
     * List all users.
     * @param request
     * @return
     */
    @ApiOperation(value = "查询所有用户", httpMethod = "GET")
    @ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true)
    @GetMapping("/search/allUser")
    public ListUsers searchAllUser(HttpServletRequest request) {

        ResponseEntity<Object> response = (ResponseEntity<Object>) userOperate(request, Operate.ALL_SEARCH, null);

        ObjectMapper objectMapper = new ObjectMapper();
        ListUsers ListUsers = null;
        try {
            ListUsers = objectMapper.readValue(response.getBody().toString(), ListUsers.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ListUsers;
    }


    /***
     * Create the specified workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "创建企业空间", httpMethod = "POST")
    @ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true)
    @PostMapping("/create/workspace")
    public CreateWorkspaceResponse createWorkspace(HttpServletRequest request, @RequestBody @Valid CreateWorkspaceRequest createWorkspaceRequest) {
        //return workSpaceOperate(request, WorkspaceOperate.CREATE, createWorkspace);

        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.CREATE, createWorkspaceRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        CreateWorkspaceResponse createWorkspaceResponse = null;
        try {
            createWorkspaceResponse = objectMapper.readValue(response.getBody().toString(), CreateWorkspaceResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return createWorkspaceResponse;
    }

    /***
     * Describe the specified workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "查询企业空间", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "workspace name", name = "workspace", dataType = "String", paramType = "header", required = true)})
    @GetMapping("/search/workspace")
    public SearchWorkspaceResponse searchWorkspace(HttpServletRequest request) {
        //return workSpaceOperate(request, WorkspaceOperate.SEARCH, null);
        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.SEARCH, null);

        ObjectMapper objectMapper = new ObjectMapper();
        SearchWorkspaceResponse searchWorkspaceResponse = null;
        try {
            searchWorkspaceResponse = objectMapper.readValue(response.getBody().toString(), SearchWorkspaceResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return searchWorkspaceResponse;
    }

    /***
     * Delete the specified workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "删除企业空间", httpMethod = "DELETE")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "workspace name", name = "workspace", dataType = "String", paramType = "header", required = true)})
    @DeleteMapping("/delete/workspace")
    public DeleteWorkspaceResponse deleteWorkspace(HttpServletRequest request) {
        //return workSpaceOperate(request, WorkspaceOperate.DELETE, null);

        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.DELETE, null);

        ObjectMapper objectMapper = new ObjectMapper();
        DeleteWorkspaceResponse deleteWorkspaceResponse = null;
        try {
            deleteWorkspaceResponse = objectMapper.readValue(response.getBody().toString(), DeleteWorkspaceResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return deleteWorkspaceResponse;
    }

    /***
     * Invite a member to the specified workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "企业空间邀请成员", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "workspace name", name = "workspace", dataType = "String", paramType = "header", required = true)})
    @PostMapping("/create/workspaces/member")
    public ResponseNormal inviteWorkspaceMember(HttpServletRequest request, @RequestBody @Valid InviteUser inviteUser) {
        //return workSpaceOperate(request, WorkspaceOperate.INVITE_MEMBER, inviteUser);

        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.INVITE_MEMBER, inviteUser);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNormal responseNormal = null;
        try {
            responseNormal = objectMapper.readValue(response.getBody().toString(), ResponseNormal.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return responseNormal;
    }

    /***
     * List all members in the specified workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "企业空间查询所有成员", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "workspace name", name = "workspace", dataType = "String", paramType = "header", required = true)})
    @GetMapping("/search/workspaces/allMember")
    public ListUsers searchAllMember(HttpServletRequest request) {
        //return workSpaceOperate(request, WorkspaceOperate.SEARCH_ALL_MEMBER, null);

        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.SEARCH_ALL_MEMBER, null);

        ObjectMapper objectMapper = new ObjectMapper();
        ListUsers ListUsers = null;
        try {
            ListUsers = objectMapper.readValue(response.getBody().toString(), ListUsers.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ListUsers;
    }

    /***
     * Describe the specified user in the given workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "企业空间查询成员", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "workspace name", name = "workspace", dataType = "String", paramType = "header", required = true),
            @ApiImplicitParam(value = "username", name = "member", dataType = "String", paramType = "header", required = true)})
    @GetMapping("/search/workspaces/member")
    public SearchWorkspaceMemberResponse searchMember(HttpServletRequest request) {

        //return workSpaceOperate(request, WorkspaceOperate.SEARCH_MEMBER, null);

        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.SEARCH_MEMBER, null);

        ObjectMapper objectMapper = new ObjectMapper();
        SearchWorkspaceMemberResponse searchWorkspaceMemberResponse = null;
        try {
            searchWorkspaceMemberResponse = objectMapper.readValue(response.getBody().toString(), SearchWorkspaceMemberResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return searchWorkspaceMemberResponse;
    }

    /***
     * Remove the specified member from the workspace.
     * @param request
     * @return
     */
    @ApiOperation(value = "企业空间移除成员", httpMethod = "DELETE")
    @ApiImplicitParams({@ApiImplicitParam(value = "Authorizations", name = "access_token", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "workspace name", name = "workspace", dataType = "String", paramType = "header", required = true),
            @ApiImplicitParam(value = "username", name = "member", dataType = "String", paramType = "header", required = true)})
    @DeleteMapping("/delete/workspaces/member")
    public ResponseNormal deleteMember(HttpServletRequest request) {
        //return workSpaceOperate(request, WorkspaceOperate.DELETE_MEMBER, null);
        ResponseEntity<Object> response = (ResponseEntity<Object>) workSpaceOperate(request, WorkspaceOperate.DELETE_MEMBER, null);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNormal responseNormal = null;
        try {
            responseNormal = objectMapper.readValue(response.getBody().toString(), ResponseNormal.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return responseNormal;

    }


    public Object userOperateExcute(String accessToken, String username, Operate operate, Object body) {
        try {
            HttpMethod methodType = null;
            String operateType = "";
            switch (operate) {
                case ALL_SEARCH:
                case SEARCH:
                    methodType = HttpMethod.GET;
                    operateType = "search";
                    break;
                case DELETE:
                    methodType = HttpMethod.DELETE;
                    operateType = "delete";
                    break;
                case UPDATE:
                    methodType = HttpMethod.PUT;
                    operateType = "update";
                    break;
                case CREATE:
                    methodType = HttpMethod.POST;
                    operateType = "create";
                    break;
                default:
                    return "method is err! please check it first!";
            }

            //封装http消息

            String url;
            if (operate == Operate.DELETE || operate == Operate.UPDATE || operate == Operate.SEARCH) {
                url = String.format("%s%s/%s", proxyProperties.getKubesphere().getHost()
                        , proxyProperties.getKubesphere().getUserManageUrl(), username);
            } else {
                url = proxyProperties.getKubesphere().getHost() +
                        proxyProperties.getKubesphere().getUserManageUrl();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            //发送http请求并解析响应
            logger.info("send http request url : " + url + ", operateType = " + operateType);
            //String body = HttpClient.sendHtttpReq(url, headers, HttpMethod.GET, null);

            HttpEntity<Object> entity = entity = new HttpEntity<>(body, headers);

            //restTemplate.setErrorHandler(new CustomResponseErrorHandler());

            //使用Object则返回格式为字段=值
/*            ResponseEntity<Object> exchange = restTemplate.exchange(url,
                    methodType, entity, Object.class);*/

            //使用String则返回格式为字段:值
            ResponseEntity<String> exchange = restTemplate.exchange(url,
                    methodType, entity, String.class);

            logger.info("recv http response message : " + exchange);

            return exchange;

        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e.getBody());
            //System.out.println(e.getMessage());
            //e.printStackTrace();
            //System.out.println(e.getMessage());
            //return e.getMessage();
            throw e;
        }

        //return "something was wrong! please check it first!
    }

    /**
     * 操作用户，包括增删改查
     *
     * @param request
     * @param operate
     * @param body
     * @return
     */
    public Object userOperate(HttpServletRequest request, Operate operate, Object body) {
        String accessToken;

        //自验证时可以改为false，上线时改为true
        if(proxyProperties.getKubesphere().isAuthFlag() == false){
            ObjectMapper objectMapper = new ObjectMapper();
            UserTokenResponse userTokenResponse = null;
            try {
                ResponseEntity<Object> tokenResponse = (ResponseEntity<Object>) getAuthToken(new UserTokenRequest(proxyProperties.getKubesphere().getRoot(),
                        proxyProperties.getKubesphere().getPasswd()));
                userTokenResponse = objectMapper.readValue(tokenResponse.getBody().toString(), UserTokenResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            accessToken = userTokenResponse.getAccess_token();
        }else{
            accessToken = getToken(request);
        }
        //
        if (accessToken == null) {
            return "token is null! please check it first!";
        }
        //封装http消息

        String url;
        String username = null;
        if (operate == Operate.DELETE || operate == Operate.UPDATE || operate == Operate.SEARCH) {
            username = request.getHeader(proxyProperties.getKubesphere().getFieldName_username());

        }

        return userOperateExcute(accessToken, username, operate, body);


    }

    /**
     * 操作企业空间及企业空间成员，包括增删改查
     *
     * @param request
     * @param operate
     * @param body
     * @return
     */
    public Object workSpaceOperate(HttpServletRequest request, WorkspaceOperate operate, Object body) {
        try {
            //自验证时可以改为false，上线时改为true
            String accessToken;
            if(proxyProperties.getKubesphere().isAuthFlag() == false) {
                ObjectMapper objectMapper = new ObjectMapper();
                UserTokenResponse userTokenResponse = null;
                try {
                    ResponseEntity<Object> tokenResponse = (ResponseEntity<Object>) getAuthToken(new UserTokenRequest(proxyProperties.getKubesphere().getRoot(),
                            proxyProperties.getKubesphere().getPasswd()));
                    userTokenResponse = objectMapper.readValue(tokenResponse.getBody().toString(), UserTokenResponse.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                accessToken = userTokenResponse.getAccess_token();
            }else{
                accessToken = getToken(request);
            }

            if (accessToken == null) {
                return "token is null! please check it first!";
            }

            String url = null;
            HttpMethod methodType = null;
            String member = null;
            String workSpaceName = null;

            switch (operate) {
                case CREATE:
                    methodType = HttpMethod.POST;
                    url = proxyProperties.getKubesphere().getHost() +
                            proxyProperties.getKubesphere().getCreateWorkSpaceUrl();
                    break;
                case SEARCH:
                    methodType = HttpMethod.GET;
                    workSpaceName = request.getHeader(proxyProperties.getKubesphere().getFieldName_workspace());
                    url = String.format("%s%s/%s", proxyProperties.getKubesphere().getHost(),
                            proxyProperties.getKubesphere().getCreateWorkSpaceUrl(), workSpaceName);
                    break;
                case DELETE:
                    methodType = HttpMethod.DELETE;
                    workSpaceName = request.getHeader(proxyProperties.getKubesphere().getFieldName_workspace());
                    url = String.format("%s%s/%s", proxyProperties.getKubesphere().getHost(),
                            proxyProperties.getKubesphere().getDeleteWorkSpaceUrl(), workSpaceName);
                    break;

                case INVITE_MEMBER:
                    methodType = HttpMethod.POST;
                    workSpaceName = request.getHeader(proxyProperties.getKubesphere().getFieldName_workspace());
                    url = String.format("%s%s/%s/members", proxyProperties.getKubesphere().getHost(),
                            proxyProperties.getKubesphere().getWorkSpaceMemberUrl(), workSpaceName);
                    break;
                case SEARCH_ALL_MEMBER:
                    methodType = HttpMethod.GET;
                    workSpaceName = request.getHeader(proxyProperties.getKubesphere().getFieldName_workspace());
                    url = String.format("%s%s/%s/members", proxyProperties.getKubesphere().getHost(),
                            proxyProperties.getKubesphere().getWorkSpaceMemberUrl(), workSpaceName);
                    break;
                case SEARCH_MEMBER:
                    methodType = HttpMethod.GET;
                    member = request.getHeader(proxyProperties.getKubesphere().getFieldName_member());
                    workSpaceName = request.getHeader(proxyProperties.getKubesphere().getFieldName_workspace());
                    url = String.format("%s%s/%s/members/%s", proxyProperties.getKubesphere().getHost(),
                            proxyProperties.getKubesphere().getWorkSpaceMemberUrl(), workSpaceName, member);
                    break;
                case DELETE_MEMBER:
                    methodType = HttpMethod.DELETE;
                    member = request.getHeader(proxyProperties.getKubesphere().getFieldName_member());
                    workSpaceName = request.getHeader(proxyProperties.getKubesphere().getFieldName_workspace());
                    url = String.format("%s%s/%s/members/%s", proxyProperties.getKubesphere().getHost(),
                            proxyProperties.getKubesphere().getWorkSpaceMemberUrl(), workSpaceName, member);
                    break;

                default:
                    return "method is err! please check it first!";
            }

            //封装http消息

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            //发送http请求并解析响应
            logger.info("send http request url : " + url);
            //String body = HttpClient.sendHtttpReq(url, headers, HttpMethod.GET, null);

            HttpEntity<Object> entity = entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> exchange = restTemplate.exchange(url,
                    methodType, entity, String.class);

            logger.info("recv http response message : " + exchange);

            return exchange;

        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e.getMessage());
            //return e.getMessage();
            throw e;
        }

        //return "something was wrong! please check it first!";

    }


    /**
     * 根据用户名密码获取kubesphere的access_token
     *
     * @param
     * @return
     */
    public Object getAuthToken(UserTokenRequest userTokenRequest) throws Exception {
        try {

            //封装http消息
            String url = proxyProperties.getKubesphere().getHost() +
                    proxyProperties.getKubesphere().getTokenUrl();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //发送http请求并解析响应
            logger.info("send request token url : " + url);

            HttpEntity<Object> entity = new HttpEntity<>(userTokenRequest, headers);

            ResponseEntity<String> exchange = restTemplate.exchange(url,
                    HttpMethod.POST, entity, String.class);

            logger.info("recv http response message : " + exchange);

            return exchange;

        } catch (Exception e) {
            //e.printStackTrace();
            //return e.getMessage();
            throw e;
        }

    }

    /**
     * 根据用户名密码获取kubesphere的access_token
     *
     * @param
     * @return
     */
    /*
    public String getAuthToken(UserTokenRequest userTokenRequest) {
        try {
            //获取username
            String username = userTokenRequest.getUsername();
            //获取password
            String password = userTokenRequest.getPassword();

            if (username == null || password == null) {
                logger.warn("用户名或密码为空！");
                return null;
            }

            //封装http消息
            String url = proxyProperties.getKubesphere().getHost() +
                    proxyProperties.getKubesphere().getTokenUrl();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("username", username);
            jsonObj.put("password", password);

            logger.info("kubesphere user info : username = " + username + "password = " + password);

            //发送http请求并解析响应
            logger.info("send request token url : " + url);
            String body = HttpClient.sendHtttpReq(url, headers, HttpMethod.POST, jsonObj);

            logger.info("recv token response body : " + body);

            JSONObject bodyJson = JSONObject.parseObject(body);

            String accessToken = bodyJson.get("access_token").toString();

            logger.info(" accessToken = " + accessToken);

            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }*/

    /**
     * 从url参数获取access_token
     *
     * @param request
     * @return
     */
    public String getTokenFromUrl(HttpServletRequest request) {
        //打印接收到的http参数
        Map<String, String> httpParamsMap = HttpHandleTools.getHttpParamsMap(request);

        //获取username
        String accessToken = httpParamsMap.get(proxyProperties.getKubesphere().getFieldName_token());
        if (accessToken.isEmpty()) {
            logger.warn("func getTokenFromUrl: token is null! please check it first!");
            return null;
        }
        return accessToken;
    }

    /**
     * 根据配置决策读取token的方式
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        if (proxyProperties.getKubesphere().getTokenType() == ConstentDef.TOKEN_FROM_HEADER) {
            return request.getHeader("accessToken");
        } else if (proxyProperties.getKubesphere().getTokenType() == ConstentDef.TOKEN_FROM_URL) {
            return getTokenFromUrl(request);
        } else {
            return null;
        }
    }
}
