package com.cobra.proxy.iamController;

import com.alibaba.fastjson.JSONObject;
import com.cobra.proxy.model.iam.*;
import com.cobra.proxy.model.kubesphere.CreateUser;
import com.cobra.proxy.model.kubesphere.UserTokenRequest;
import com.cobra.proxy.model.kubesphere.UserTokenResponse;
import com.cobra.proxy.properties.ProxyProperties;
import com.cobra.proxy.util.ConstentDef;
import com.cobra.proxy.util.HttpClient;
import com.cobra.proxy.util.HttpHandleTools;
import com.cobra.proxy.util.Operate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: iam-universal-sso-proxy
 * @BelongsPackage: com.cobra.proxy.controller
 * @Author: t480
 * @CreateTime: 2020-08-10 09:32
 * @Description: iam账号管理
 */

@RestController
@RequestMapping("/api")
public class IamAccountManageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProxyProperties proxyProperties;

    public static RestTemplate restTemplate = new RestTemplate();


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
                    logger.info("func queryUser: search user SUCCESS! ");
                    return new ResultResponse("SUCCESS", "true");
                } else {
                    logger.info("func queryUser: search user FAIL! ");
                    return new ResultResponse("SUCCESS", "false");
                }
            } catch (Exception e) {
                logger.info("func queryUser: user " + accountName + " is not exist!");
                return new ResultResponse("SUCCESS", "false");
            }

        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("func queryUser: query err ! " + e.getMessage());
            return new ResultResponse("SUCCESS", "false");
        }

    }

    @ApiOperation(value = "用户推送", httpMethod = "POST")
    @PostMapping("/pushUser")
    public ResultResponse pushUser(@RequestBody UserPushRequest userPushRequest){
        try {
            logger.info("pushUser request : " + userPushRequest);
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
            //String accountName = userPushRequest.getAccountName();

            String accountName = userPushRequest.getMappingAttr().getUsername();
            String status = userPushRequest.getStatus();

            if(proxyProperties.getKubesphere().getRoot().equals(accountName)){
                logger.info("func pushUser: ERROR! " + ConstentDef.OPERATE_EXCEPTION_MSG);
                return new ResultResponse("ERROR", "",ConstentDef.OPERATE_EXCEPTION_MSG);
            }

            // 更新和新增，这里只处理新增
            if ("UPSERT".equals(status)) {
                try {

                    try {
                        //使用账号查询是否存在，如果存在则直接返回成功，不存在则创建。
                        ResponseEntity<Object> response = (ResponseEntity<Object>) userOperateExcute(privateToken, accountName, Operate.SEARCH, null);
                        if (response.getStatusCode().value() == 200) {
                            logger.info("func pushUser: ERROR! " + ConstentDef.CREATE_WARN_MSG);
                            return new ResultResponse("ERROR", "", ConstentDef.CREATE_WARN_MSG);
                        }
                    } catch (Exception e) {
                        logger.info("user " + accountName + " is not exist!");
                    }
/*                    CreateUser createUser = new CreateUser(userPushRequest.getAccountName(),
                            proxyProperties.getKubesphere().getDefaultPassword(), userPushRequest.getMappingAttr().getEmail(),
                            proxyProperties.getKubesphere().getDefaultClusterRole()); */
                    CreateUser createUser = new CreateUser(accountName,
                            proxyProperties.getKubesphere().getDefaultPassword(), userPushRequest.getMappingAttr().getEmail());
                    ResponseEntity<String> resBody = (ResponseEntity<String>) userOperateExcute(privateToken, accountName, Operate.CREATE, createUser);
                    if (resBody.getStatusCode().value() == 200) {
                        //return new ResultResponse("SUCCESS");
                        logger.info("func pushUser: create user SUCCESS! ");
                        return new ResultResponse("SUCCESS");

                    } else {
                        logger.info("func pushUser: ERROR! " + ConstentDef.CREATE_ERR_MSG);
                        return new ResultResponse("ERROR", "", ConstentDef.CREATE_ERR_MSG);
                    }

                } catch (Exception e) {
                    logger.error("func pushUser: ERROR! " + e.getMessage());
                    return new ResultResponse("ERROR", "",e.getMessage());
                }

            } else if ("DELETE".equals(status)) {
                try {
                    ResponseEntity<String> resBody = (ResponseEntity<String>) userOperateExcute(privateToken, accountName, Operate.DELETE, null);
                    if (resBody.getStatusCode().value() == 200) {
                        logger.info("func pushUser: delete user SUCCESS! ");
                        return new ResultResponse("SUCCESS");
                    } else {
                        logger.info("func pushUser: ERROR! " + ConstentDef.DELETE_ERR_MSG);
                        return new ResultResponse("ERROR", "", ConstentDef.DELETE_ERR_MSG);
                    }

                } catch (Exception e) {
                    logger.error("func pushUser: ERROR! " + e.getMessage());
                    return new ResultResponse("ERROR", "",e.getMessage());
                }
            }

            logger.info("func pushUser: default response SUCCESS! ");
            return new ResultResponse("SUCCESS");
        }catch (Exception e) {
            //e.printStackTrace();
            logger.error("func pushUser: query err ! " + e.getMessage());
            return new ResultResponse("ERROR", "false", e.getMessage());
        }

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
            logger.info("send http request url : " + url + ", operateType = " + operateType + ",body = " + body);
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

}
