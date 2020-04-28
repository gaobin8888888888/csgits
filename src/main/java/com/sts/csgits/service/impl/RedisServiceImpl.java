package com.sts.csgits.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.csgits.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ：gb
 * @date ：Created in 2020/3/1 16:52
 */
@Slf4j
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisServiceImpl set error {}", e);
        }
        return false;
    }

    @Override
    public Object get(String key) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            return object;
        } catch (Exception e) {
            log.error("RedisServiceImpl get error {}", e);
        }
        return null;
    }

    @Override
    public void del(String key){
        try {
            redisTemplate.delete(key);
        }catch (Exception e){
            log.error("RedisServiceImpl del error {}", e);
        }
    }

    @Override
    public void incr(String key, long num){
        try {
            redisTemplate.opsForValue().increment(key, num);
        } catch (Exception e) {
            log.error("RedisServiceImpl incr error {}", e);
        }
    }

    @Override
    public void decr(String key, long num){
        try {
            redisTemplate.opsForValue().decrement(key, num);
        } catch (Exception e) {
            log.error("RedisServiceImpl decr error {}", e);
        }
    }

    @Override
    public <T> boolean convertAndSend(String channel, T message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            log.info("[convert] " + channel + " " + json);
            redisTemplate.convertAndSend(channel, json);
        } catch (JsonProcessingException e) {
            log.error("RedisService.convertAndSendAnother  channel :{} message:{} error : {}", channel, message, e);
            return false;
        }
        return true;
    }


}
