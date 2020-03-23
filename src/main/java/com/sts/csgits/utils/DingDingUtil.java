package com.sts.csgits.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：gb
 * @date ：Created in 2019/12/7 11:25
 */
public class DingDingUtil {

    public final static Logger logger = LoggerFactory.getLogger(DingDingUtil.class);

    //VNC平台新增账号时通过钉钉提醒
    public static void sendRemind(String url, String message){
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            JSONObject content = new JSONObject();
            content.put("content", message);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msgtype", "text");
            jsonObject.put("text", content);
            //HttpUtil.post(url, jsonObject.toString(), headers);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("请求钉钉异常{}", e);
        }
    }
}
