package com.cobra.proxy.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cobra.proxy.model.iam.TokenInfo;
import com.cobra.proxy.model.iam.UserInfo;
import com.cobra.proxy.properties.ProxyProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class IAMUtil {
	
	private Logger log = LoggerFactory.getLogger(IAMUtil.class);
	
	@Autowired 
	public ProxyProperties proxyProperties;

	public static ObjectMapper mapper = new ObjectMapper();

	public static RestTemplate restTemplate = new RestTemplate();

	/**
	 * 通过回调code获取访问token
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public TokenInfo getToken(String code) throws JsonParseException, JsonMappingException, IOException {

		Map<String, String> param = new HashMap<String, String>();
		param.put("appcode", proxyProperties.getIam().getAppCode());
		param.put("secret", proxyProperties.getIam().getAppSecret());
		param.put("code", code);
		String urlParam = queryParam(proxyProperties.getIam().getAccessTokenUrl(), param);
		log.info("========fetch token URL=======>" + urlParam);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlParam, String.class);
		log.info("========fetch token response=======>" + responseEntity.getBody());

		if (responseEntity.getBody() != null) {
			TokenInfo token = mapper.readValue(responseEntity.getBody(), TokenInfo.class);
			return token;
		}
		return null;
	}

	/**
	 * 通过访问token换取用户信息
	 * 
	 * @param token
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public UserInfo getUserInfo(TokenInfo token) throws JsonParseException, JsonMappingException, IOException {

		Map<String, String> param = new HashMap<String, String>();
		param.put("appcode", proxyProperties.getIam().getAppCode());
		param.put("secret", proxyProperties.getIam().getAppSecret());
		param.put("token", token.getAccessToken());
		String urlParam = queryParam(proxyProperties.getIam().getUserInfoUrl(), param);
		log.info("========fetch userinfo URL=======>" + urlParam);

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlParam, String.class);
		log.info("========fetch userinfo response=======>" + responseEntity.getBody());

		if (responseEntity.getBody() != null) {
			UserInfo userInfo = mapper.readValue(responseEntity.getBody(), UserInfo.class);
			return userInfo;
		}
		return null;
	}

	/**
	 * 参数拼接
	 * @param url
	 * @param param
	 * @return
	 */
	private static String queryParam(String url, Map<String, String> param) {

		if (url == null || param == null) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		param.forEach((k, v) -> {
			builder.append(k + "=" + v + "&");
		});

		if (builder.length() > 0) {
			builder.deleteCharAt(builder.lastIndexOf("&"));
		}
		String result = builder.length() > 0 ? "?" + builder.toString() : "";
		return url + result;
	}
}
