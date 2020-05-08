package com.sts.csgits.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 * @author ：gb
 * @date ：Created in 2020/2/10 17:00
 */
public class StringUtils {

    /**
     * 判断一个字符是否不是空字符串。
     * 如果一个字符为null或者长度为0的字符串，则被认为是Empty。
     * 长度不为0的空格符字符串也被认为not empty。
     *
     * @param string
     * @return true 字符串不为空字符串。
     */
    public static boolean isNotEmpty(String string) {
        return string != null && string.trim().length() > 0;
    }

    /**
     * 判断一个字符是否为空字符串。
     * 如果一个字符为null或者长度为0的字符串，则被认为是Empty。
     * 长度不为0的空格符字符串也被认为not empty。
     *
     * @param string
     * @return true 字符串为空字符串。
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * 获取唯一字符串
     * @return
     */
    public static String generateUniqueId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = sdf.format(new Date());
        String randomStr = String.valueOf((int)((Math.random()*9+1)*1000));
        return formatDate + randomStr;
    }

    public static String endString(String str, String regex){
        String[] splits = str.split(regex);
        return splits[splits.length - 1];
    }
}
