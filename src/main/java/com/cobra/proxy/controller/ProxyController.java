package com.cobra.proxy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.cobra.proxy.component.IAMUtil;
import com.cobra.proxy.model.iam.TokenInfo;
import com.cobra.proxy.model.iam.UserInfo;
import com.cobra.proxy.properties.ProxyProperties;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

	Logger log = LoggerFactory.getLogger(ProxyController.class);

	@Autowired
	public ProxyProperties proxyProperties;

	@Autowired
	public IAMUtil iamUtil;
/*
	@ApiOperation(value = "认证代理", httpMethod = "GET")
	@GetMapping("/authorize")
	public void authorize(HttpServletRequest request, HttpServletResponse response, String client_id,
			String redirect_uri, String response_type, String state) {
		log.info("==================method the authorize=================");
		try {
			HttpSession session = request.getSession(true);
			session.setAttribute("state", state);
			log.info("client_id:{},redirect_uri:{},response_type:{},state:{}", client_id, redirect_uri, response_type,
					state);
			httpRedirect(response, proxyProperties.getIam().getAuthorizationUrl() + "?appCode="
					+ proxyProperties.getIam().getAppCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	@ApiOperation(value = "认证代理", httpMethod = "GET")
	@GetMapping("/token")
	public TokenInfo getToken(String client_id, String client_secret, @RequestParam String code, String grant_type,
							  String redirect_uri) {
		log.info("==================method the getToken=================");
		log.info("client_id:{},client_secret:{},code:{},grant_type:{},redirect_uri:{}", client_id, client_secret, code,
				grant_type, redirect_uri);
		try {
			TokenInfo token = iamUtil.getToken(code);
			//TokenResponse tokenResponse = new TokenResponse(token.getAccessToken(), token.getExpire());
			log.info("==================getToken response:{}=================", token.toString());
			return token;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "用户信息获取代理", httpMethod = "GET")
	@GetMapping("/userinfo")
	public UserInfo getUserinfo(HttpServletRequest request) {
		log.info("==================method the getUserinfo=================");
		String authorization = request.getHeader("authorization");
		log.info("==================authorization:{}=================", authorization);
		if (StringUtils.isEmpty(authorization)) {
			log.info("获取authorization失败");
			return null;
		}
		String accessToken = authorization.split(" ")[1];
		log.info("==================accessToken:{}=================", accessToken);
		if (StringUtils.isEmpty(accessToken)) {
			log.info("获取accessToken失败");
			return null;
		}
		try {
			UserInfo userInfo = iamUtil.getUserInfo(new TokenInfo(accessToken, "3600000"));
			if (!ObjectUtils.isEmpty(userInfo)) {
/*				return new ProxUserInfo(userInfo.getAccountName(),
						String.format("%s@cnpc.com.cn", userInfo.getAccountName()));*/
				log.info("==================getUserInfo response:{}=================", userInfo.toString());
				return userInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
/*
	@ApiOperation(value = "回调代理", httpMethod = "GET")
	@GetMapping("/callback")
	public void callback(String code, HttpServletRequest request, HttpServletResponse response) {
		log.info("==================method the callback=================");
		try {
			HttpSession session = request.getSession(true);
			String state = (String) session.getAttribute("state");
			
			if(StringUtils.hasText(state)) {
				log.info("received code:{}", code);
				if (!StringUtils.isEmpty(code)) {
					String gitlabCallBack = String.format(
							proxyProperties.getGitlab().getHostName() + proxyProperties.getGitlab().getCallBackUrl(), code,
							state);
					session.removeAttribute("state");
					httpRedirect(response, gitlabCallBack);
				}
			}
			log.info("未获取到state进行重定向操作");
			httpRedirect(response, proxyProperties.getGitlab().getHostName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ApiOperation(value = "登出接口", httpMethod = "GET")
	@RequestMapping("/logout")
	public void logout(HttpServletResponse response) throws Exception {
		log.info("==================method the logout=================");
		String iamLogoutURL = proxyProperties.getIam().getLogOutUrl();
		String gitlabURL = proxyProperties.getGitlab().getHostName();
		if(StringUtils.hasText(iamLogoutURL)&&StringUtils.hasText(gitlabURL)) {
			StringBuffer logoutHtml= new StringBuffer();
			logoutHtml.append("<html> <body> <div id='logoutIAM'></div> </body> ");
			logoutHtml.append("<script type='text/javascript'>");
			logoutHtml.append(" window.onload = function () {var logoutDiv = document.getElementById('logoutIAM');var logoutImage=document.createElement('img');");
			logoutHtml.append("logoutImage.src = '");
			logoutHtml.append(iamLogoutURL+"';");
			logoutHtml.append(" setTimeout(function(){ window.location.href='");
			logoutHtml.append(gitlabURL+"'; }, 2000);");
		    logoutHtml.append(" }</script> </html>");
		    log.info("登出内容:{}",logoutHtml.toString());
			response.getOutputStream().write(logoutHtml.toString().getBytes());
		}else {
			log.info("未配置IAM登出地址或者GITLAB HOST_NAME");
		}
	}

	public static void httpRedirect(HttpServletResponse response, String redirectUrl) throws IOException {
		System.out.println("Redirecting user to " + redirectUrl);
		StringBuffer htmlStart = new StringBuffer("<html><head><script>window.location.href=\"" + redirectUrl
				+ "\"</script></head><body>Please wait...</body></html>");
		response.getOutputStream().write(htmlStart.toString().getBytes());
	}
*/
}
