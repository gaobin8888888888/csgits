package com.sts.csgits.service;

import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.entity.Notice;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface CreateRecordService extends BaseService<CreateRecord, Integer> {

    /**
     * 根据条件查询
     * @param createRecord
     * @return
     */
    public List<CreateRecord> selectByCreateRecord(CreateRecord createRecord);

    /**
     * 获取最新创建的记录信息
     * @return
     */
    public CreateRecord selectOne();
}
