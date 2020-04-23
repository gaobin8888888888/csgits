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
     * 每次加1
     * @param key
     */
    public void incr(String key);

    /**
     * 减1
     * @param key
     */
    public void decr(String key);

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
