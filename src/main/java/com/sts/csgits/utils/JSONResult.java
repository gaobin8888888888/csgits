package com.sts.csgits.utils;

import com.alibaba.fastjson.JSONObject;
import com.sts.csgits.inc.Const;

/**
 * json格式的响应
 * @author ：gb
 * @date ：Created in 2020/3/7 12:59
 */
public class JSONResult {

    /**
     * 表示响应状态，规定"0"表示成功，"1"表示失败
     */
    private Integer status;

    /**
     * 响应信息
     */
    private String message;

    /**
     * json格式的对象数据
     */
    private Object data;

    public JSONResult() {

    }

    public JSONResult(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static String errorInstance(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", Const.RETURN_ERROR);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }

    public static String successInstance(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", Const.RETURN_SUCCESS);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }

    public static String errorInstance(String message, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", Const.RETURN_ERROR);
        jsonObject.put("message", message);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }

    public static String successInstance(String message, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", Const.RETURN_SUCCESS);
        jsonObject.put("message", message);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
