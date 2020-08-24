package com.cobra.proxy.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @BelongsProject: spring-boot-01
 * @BelongsPackage: com.bigdata.httpUtils
 * @Author: t480
 * @CreateTime: 2020-07-16 15:03
 * @Description: http client
 */
public class HttpClient {
    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param params    发送的参数
     * @return  AdToutiaoJsonTokenData
     */
    public static String sendPostRequest(String url, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity,String .class);

        return response.getBody();
    }

    /**
     * 向目的URL发送get请求
     * @param url       目的url
     * @param params    发送的参数
     * @param headers   发送的http头，可在外部设置好参数后传入
     * @return  String
     */
    public static String sendGetRequest(String url, MultiValueMap<String, String> params, HttpHeaders headers,
                                        Map<String, Object> map){
        RestTemplate client = new RestTemplate();

        HttpMethod method = HttpMethod.GET;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class, map);
//        ResponseEntity<String> response = client.exchange(url, method, null, String.class, params);

        //System.out.println(exchange.getBody());
        //System.out.println();
        return response.getBody();
    }

    /**
     * 向目的URL发送get请求
     * @param url       目的url
     * @param params    发送的参数
     * @param headers   发送的http头，可在外部设置好参数后传入
     * @return  String
     */
    public static String  sendHtttpGetReq(String url, HttpHeaders headers, Map<String, Object> params) {
        RestTemplate client = new RestTemplate();

        HttpMethod method = HttpMethod.GET;

        HttpEntity<String> formEntity = new HttpEntity<String>(null, headers);

        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        //执行HTTP请求，将返回的结构使用String 类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class, params);

        return response.getBody();

    }

    public static String sendHtttpReq(String url, HttpHeaders headers, HttpMethod method, JSONObject jsonObj) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity;

        if(jsonObj == null){
            entity = new HttpEntity<>(null, headers);
        }else{
            entity = new HttpEntity<>(jsonObj.toString(), headers);
        }

        ResponseEntity<String> exchange = restTemplate.exchange(url,
                method, entity, String.class);

        //System.out.println("body = " + exchange.getBody());

        return exchange.getBody();
    }

    public static Object sendHtttpReq2(String url, HttpHeaders headers, HttpMethod method, JSONObject jsonObj) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity;

        if(jsonObj == null){
            entity = new HttpEntity<>(null, headers);
        }else{
            entity = new HttpEntity<>(jsonObj.toString(), headers);
        }

        ResponseEntity<String> exchange = restTemplate.exchange(url,
                method, entity, String.class);

        //System.out.println("body = " + exchange.getBody());

        return exchange;
    }

    public static Object sendHtttpReq3(String url, HttpHeaders headers, HttpMethod method, Object body) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity;

        if(body == null){
            entity = new HttpEntity<>(null, headers);
        }else{
            entity = new HttpEntity<>(body, headers);
        }

        ResponseEntity<String> exchange = restTemplate.exchange(url,
                method, entity, String.class);

        //System.out.println("body = " + exchange.getBody());

        return exchange;
    }
}