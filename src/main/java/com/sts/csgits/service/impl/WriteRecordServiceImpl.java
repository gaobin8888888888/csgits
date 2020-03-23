package com.sts.csgits.service.impl;

import com.sts.csgits.dao.WriteRecordMapper;
import com.sts.csgits.entity.WriteRecord;
import com.sts.csgits.service.WriteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("writeRecordService")
public class WriteRecordServiceImpl extends BaseServiceImpl<WriteRecord, Integer> implements WriteRecordService {

    @Autowired
    private WriteRecordMapper writeRecordMapper;


    @Override
    public List<WriteRecord> selectAll(Integer writeRecordId) {
        return writeRecordMapper.selectAll(writeRecordId);
    }
}
