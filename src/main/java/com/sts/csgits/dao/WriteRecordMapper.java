package com.sts.csgits.dao;

import com.sts.csgits.entity.WriteRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface WriteRecordMapper extends BaseMapper<WriteRecord, Integer> {

    /**
     * 查询一个填写记录中所有的记录
     * @param createRecordId
     * @return
     */
    public List<WriteRecord> selectAll(@Param("createRecordId") Integer createRecordId);


}
