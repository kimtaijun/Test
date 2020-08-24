package com.cobra.proxy.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: springboot-k8
 * @BelongsPackage: com.bigdata.tools
 * @Author: t480
 * @CreateTime: 2020-07-22 14:54
 * @Description: http handle tools
 */
public class HttpHandleTools {

    public static Map getHttpParamsMap(HttpServletRequest request){

        Logger logger = LoggerFactory.getLogger(HttpHandleTools.class);

        Map<String, String> params = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                if (paramValues[0].length() != 0) {
                    params.put(paramName, paramValues[0]);
                }
            }
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            logger.debug("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        return params;
    }


    public static JSONObject setUserOpeareteJsonBody(HttpServletRequest request, Operate operate){
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("cluster_role","cluster-regular");
        jsonObj.put("description","nomal test");
        jsonObj.put("email","test333@kubesphere.io");
        jsonObj.put("lang","en");
        jsonObj.put("password","1qaz@WSX");
        jsonObj.put("username","test333");

        if(operate == Operate.UPDATE){

            jsonObj.put("current_password","1qaz@WSX");
        }

        return jsonObj;
    }
}
