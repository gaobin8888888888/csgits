package com.sts.csgits.dao;

import com.sts.csgits.entity.WriteRecordData;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/5/5 20:51
 */
public interface WriteRecordDataMapper extends BaseMapper<WriteRecordData, Integer>  {

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    public List<WriteRecordData> selectByCondition(Condition condition);

    /**
     * 根据条件删除
     * @param condition
     * @return
     */
    public int deleteByCondition(Condition condition);
}
