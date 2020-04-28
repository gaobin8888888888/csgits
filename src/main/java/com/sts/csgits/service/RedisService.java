package com.sts.csgits.service;

/**
 * @author ：gb
 * @date ：Created in 2020/3/1 17:04
 */
public interface RedisService {

    public boolean set(String key, Object value);

    public Object get(String key);

    public void del(String key);

    /**
     * 每次加num
     * @param key
     */
    public void incr(String key, long num);

    /**
     * 减num
     * @param key
     */
    public void decr(String key, long num);

    /**
     * 发布消息到Channel并同步
     *
     * @param channel
     * @param message
     * @param <T>
     * @return
     */
    <T> boolean convertAndSend(String channel, T message);
}
