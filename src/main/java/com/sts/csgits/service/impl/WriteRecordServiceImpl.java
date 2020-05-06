package com.sts.csgits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sts.csgits.dao.WriteRecordMapper;
import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.entity.WriteRecord;
import com.sts.csgits.entity.WriteRecordData;
import com.sts.csgits.service.CreateRecordService;
import com.sts.csgits.service.WriteRecordService;
import com.sts.csgits.utils.Condition;
import com.sts.csgits.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Slf4j
@Service("writeRecordService")
public class WriteRecordServiceImpl extends BaseServiceImpl<WriteRecord, Integer> implements WriteRecordService {

    @Autowired
    private WriteRecordMapper writeRecordMapper;

    @Autowired
    private CreateRecordService createRecordService;

    @Override
    public WriteRecord selectOneWriteRecordBySole(Integer createRecordId, String sole){
        return writeRecordMapper.selectOneWriteRecordBySole(createRecordId, sole);
    }

    @Override
    public List<WriteRecord> selectAll(Integer writeRecordId) {
        return writeRecordMapper.selectAll(writeRecordId);
    }

    @Override
    public void countWriteRecordData(){
        log.info("countWriteRecordData start...");
        try {
            Condition condition = Condition.newInstance();
            String nowTime = DateUtil.format(new Date(), DateUtil.FMT_DATE_YYYY_MM_DD_HH_mm_ss);
            condition.addMapCondition("nowTime", nowTime);
            CreateRecord createRecord = createRecordService.selectByCondition(condition);
            if (createRecord != null){
                WriteRecordData writeRecordData = new WriteRecordData();
                JSONObject relatedDataJson = new JSONObject();
                JSONObject noRelatedDataJson = new JSONObject();
                List<WriteRecord> writeRecordList = selectAll(createRecord.getId());
                writeRecordData.setCreateRecordId(createRecord.getId());
                writeRecordData.setCurrentNum(writeRecordList == null ? 0 : writeRecordList.size());
                writeRecordData.setDate(nowTime);
                int relatedNum = 0;
                int noRelatedNum = 0;
                if (writeRecordList != null && writeRecordList.size() > 0){
                    for (WriteRecord writeRecord : writeRecordList){

                    }
                }
                writeRecordData.setRelatedData(relatedDataJson.toJSONString());
                writeRecordData.setNoRelatedData(noRelatedDataJson.toJSONString());
            }
        }catch (Exception e){
            log.error("countWriteRecordData error {}", e);
        }
        log.info("countWriteRecordData end...");
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject.toJSONString());
        String nowTime = DateUtil.format(new Date(), DateUtil.FMT_DATE_YYYY_MM_DD_HH_mm_ss);
        System.out.println(nowTime);
    }
}
