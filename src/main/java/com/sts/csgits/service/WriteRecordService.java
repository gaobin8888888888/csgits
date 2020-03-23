package com.sts.csgits.service;

import com.sts.csgits.entity.WriteRecord;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface WriteRecordService extends BaseService<WriteRecord, Integer> {

    /**
     * 查询所有
     * @param writeRecordId
     * @return
     */
    public List<WriteRecord> selectAll(Integer writeRecordId);
}
