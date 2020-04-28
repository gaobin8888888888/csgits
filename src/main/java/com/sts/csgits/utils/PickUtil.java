package com.sts.csgits.utils;

import com.sts.csgits.inc.Const;
import com.sts.csgits.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 比较冗余的代码提取出来
 * @author ：gb
 * @date ：Created in 2020/4/28 16:37
 */
@Service
public class PickUtil {

    @Autowired
    private RedisService redisService;

    /**
     * 增加或者减少人数
     * @param schoolId
     * @param num
     */
    public void addOrDescPeopleNum(Integer schoolId, long num){
        redisService.incr(Const.PEOPLE_NUM_TOTAL, num);
        String schookKey = String.format(Const.PEOPLE_SCHOOL_NUM_TOTAL, schoolId);
        redisService.incr(schookKey, num);

        redisService.incr(Const.PEOPLE_NUM_NEW_ADD, num);
        schookKey = String.format(Const.PEOPLE_SCHOOL_NUM_NEW_ADD, schoolId);
        redisService.incr(schookKey, num);
    }

    /**
     * 增加填写记录数
     * @param schoolId
     * @param num
     */
    public void addWriteRecordNum(Integer schoolId, long num){
        redisService.incr(Const.WRITE_RECORD_NUM_TOTAL, num);
        String schookKey = String.format(Const.WRITE_RECORD_SCHOOL_NUM_TOTAL, schoolId);
        redisService.incr(schookKey, num);

        redisService.incr(Const.WRITE_RECORD_NUM_NEW_ADD, num);
        schookKey = String.format(Const.WRITE_RECORD_SCHOOL_NUM_NEW_ADD, schoolId);
        redisService.incr(schookKey, num);
    }
}
