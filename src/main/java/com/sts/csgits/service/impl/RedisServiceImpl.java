package com.sts.csgits.service.impl;

import com.sts.csgits.inc.Const;
import com.sts.csgits.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ：gb
 * @date ：Created in 2020/3/1 16:52
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object get(String key) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
