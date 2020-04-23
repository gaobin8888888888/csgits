package com.sts.csgits.dao;

import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface CreateRecordMapper extends BaseMapper<CreateRecord, Integer> {

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

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    public CreateRecord selectByCondition(Condition condition);

}
