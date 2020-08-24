package com.cobra.proxy.util;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class HttpClientUtils {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

	public static String doGet(String url, String privateToken) {

		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).build();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(reqConfig);
		httpGet.addHeader("PRIVATE-TOKEN", privateToken);
		httpGet.addHeader("Content-Type", "application/json");

		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			log.info("httpResponse StatusCode:{}", httpResponse.getStatusLine().getStatusCode());
			result = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String doPost(String url, String jsonStr, String privateToken) {

		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).build();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(reqConfig);
		httpPost.addHeader("PRIVATE-TOKEN", privateToken);
		httpPost.addHeader("Content-Type", "application/json");

		try {
			if(StringUtils.hasText(jsonStr)) {
				StringEntity stringEntity = new StringEntity(jsonStr);
				httpPost.setEntity(stringEntity);
			}
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			log.info("httpResponse StatusCode:{}", httpResponse.getStatusLine().getStatusCode());
			result = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean doDelete(String url, String privateToken) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).build();

		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setConfig(reqConfig);
		httpDelete.addHeader("PRIVATE-TOKEN", privateToken);
		httpDelete.addHeader("Content-Type", "application/json");

		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
			log.info("httpResponse StatusCode:{}", httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 204) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
