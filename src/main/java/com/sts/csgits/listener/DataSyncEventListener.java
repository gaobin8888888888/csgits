package com.sts.csgits.listener;

import com.sts.csgits.inc.LocalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 监听重载消息
 * @author ：gb
 * @date ：Created in 2020/3/29 12:30
 */
public class DataSyncEventListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(DataSyncEventListener.class);

    @Autowired
    private LocalCache localCache;
    @Autowired
    private RedisSerializer<Object> jsonRedisSerializer;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channel = new String(message.getChannel());
            Object object = jsonRedisSerializer.deserialize(message.getBody());
            localCache.localLoad(channel, object);
        } catch (Exception e) {
            logger.error("DataSyncEventListener onMessage :{}", e);
        }
    }
}
