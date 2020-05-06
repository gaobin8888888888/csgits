package com.sts.csgits.service.impl;

import com.sts.csgits.dao.WriteRecordDataMapper;
import com.sts.csgits.entity.WriteRecordData;
import com.sts.csgits.service.WriteRecordDataService;
import com.sts.csgits.utils.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Slf4j
@Service("writeRecordDataService")
public class WriteRecordDataServiceImpl extends BaseServiceImpl<WriteRecordData, Integer> implements WriteRecordDataService {

    @Autowired
    private WriteRecordDataMapper writeRecordDataMapper;


    @Override
    public List<WriteRecordData> selectByCondition(Condition condition) {
        return writeRecordDataMapper.selectByCondition(condition);
    }
}
