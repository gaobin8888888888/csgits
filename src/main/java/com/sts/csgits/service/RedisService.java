package com.sts.csgits.service;

/**
 * @author ：gb
 * @date ：Created in 2020/3/1 17:04
 */
public interface RedisService {

    public boolean set(String key, String value);

    public Object get(String key);
}
