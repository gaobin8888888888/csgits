package com.sts.csgits.service;

import com.sts.csgits.entity.WriteRecordData;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface WriteRecordDataService extends BaseService<WriteRecordData, Integer> {

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    public List<WriteRecordData> selectByCondition(Condition condition);
}
