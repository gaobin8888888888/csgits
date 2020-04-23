package com.sts.csgits.service.impl;

import com.sts.csgits.dao.CreateRecordMapper;
import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.service.CreateRecordService;
import com.sts.csgits.utils.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("createRecordService")
public class CreateRecordServiceImpl extends BaseServiceImpl<CreateRecord, Integer> implements CreateRecordService {

    @Autowired
    private CreateRecordMapper createRecordMapper;


    @Override
    public List<CreateRecord> selectByCreateRecord(CreateRecord createRecord) {
        return createRecordMapper.selectByCreateRecord(createRecord);
    }

    @Override
    public CreateRecord selectOne() {
        return createRecordMapper.selectOne();
    }

    @Override
    public CreateRecord selectByCondition(Condition condition){
        return createRecordMapper.selectByCondition(condition);
    }
}
