package com.sts.csgits.service;

import com.sts.csgits.entity.WriteRecord;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface WriteRecordService extends BaseService<WriteRecord, Integer> {

    /**
     * 查询填写记录中一条
     * @param createRecordId
     * @return
     */
    public WriteRecord selectOneWriteRecordBySole(Integer createRecordId, String sole);

    /**
     * 查询所有
     * @param writeRecordId
     * @return
     */
    public List<WriteRecord> selectAll(Integer writeRecordId);

    /**
     * 统计填写的个人情况信息
     */
    public void countWriteRecordData();
}
