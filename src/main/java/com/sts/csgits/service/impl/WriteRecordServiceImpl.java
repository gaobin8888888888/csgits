package com.sts.csgits.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.sts.csgits.dao.WriteRecordMapper;
import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.entity.Student;
import com.sts.csgits.entity.WriteRecord;
import com.sts.csgits.entity.WriteRecordData;
import com.sts.csgits.inc.Const;
import com.sts.csgits.inc.LocalCache;
import com.sts.csgits.service.CreateRecordService;
import com.sts.csgits.service.WriteRecordDataService;
import com.sts.csgits.service.WriteRecordService;
import com.sts.csgits.utils.Condition;
import com.sts.csgits.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Multimap;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private LocalCache localCache;

    @Autowired
    private WriteRecordDataService writeRecordDataService;

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
                condition = Condition.newInstance();
                condition.addMapCondition("createRecordId", createRecord.getId());
                writeRecordDataService.deleteByCondition(condition);
                WriteRecordData writeRecordData = new WriteRecordData();
                List<WriteRecord> writeRecordList = selectAll(createRecord.getId());
                writeRecordData.setCreateRecordId(createRecord.getId());
                writeRecordData.setDate(nowTime);
                if (writeRecordList != null && writeRecordList.size() > 0){
                    Multimap<String, WriteRecord> dataMap = ArrayListMultimap.create();
                    for (WriteRecord writeRecord : writeRecordList){
                        Student student = localCache.getStudentBySole(writeRecord.getSole());
                        dataMap.put(student.getCollege(), writeRecord);
                        writeRecord.setSchoolId(student.getSchoolId());
                    }

                    Set<String> set = dataMap.keySet();
                    for (String college : set){
                        List<WriteRecord> writeRecords = ((ArrayListMultimap<String, WriteRecord>) dataMap).get(college);
                        makeData(writeRecords, writeRecordData, college);

                        Multimap<Integer, WriteRecord> schoolDataMap = ArrayListMultimap.create();
                        for (WriteRecord writeRecord : writeRecords){
                            schoolDataMap.put(writeRecord.getSchoolId(), writeRecord);
                        }
                        Set<Integer> integerSet = schoolDataMap.keySet();
                        for (Integer integer : integerSet){
                           List<WriteRecord> writeRecords1 = ((ArrayListMultimap<Integer, WriteRecord>) schoolDataMap).get(integer);
                           writeRecordData.setSchoolId(integer);
                           makeData(writeRecords1, writeRecordData, college);
                           writeRecordData.setSchoolId(null);
                        }
                    }
                }

            }
        }catch (Exception e){
            log.error("countWriteRecordData error {}", e);
        }
        log.info("countWriteRecordData end...");
    }

    public void makeData(List<WriteRecord> writeRecords, WriteRecordData writeRecordData, String college){
        int engagedNum = (int) writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_RELATED_NUM.equals(writeRecord.getRelated())).count();
        int workNum = (int) writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_WORK_NUM.equals(writeRecord.getType())).count();
        int studyNum = (int) writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_STUDY_NUM.equals(writeRecord.getType())).count();
        int collegeNum = (int) writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_DEGREE_COLLEGE_NUM.equals(writeRecord.getDegree())).count();
        int graduateNum = (int) writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_DEGREE_GRADUATE_NUM.equals(writeRecord.getDegree())).count();
        int doctorNum = writeRecords.size() - collegeNum - graduateNum;
        int hometownNum = (int)  writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_HOMETOEN_NUM.equals(writeRecord.getHome())).count();
        int salary0Num = (int)  writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_SALARY0_NUM.equals(writeRecord.getSalary())).count();
        int salary1Num = (int)  writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_SALARY1_NUM.equals(writeRecord.getSalary())).count();
        int salary2Num = (int)  writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_SALARY2_NUM.equals(writeRecord.getSalary())).count();
        int salary3Num = (int)  writeRecords.stream().filter(writeRecord -> Const.WRITE_RECORD_SALARY3_NUM.equals(writeRecord.getSalary())).count();
        int salary4Num = writeRecords.size() - salary0Num -salary1Num - salary2Num - salary3Num;

        writeRecordData.setCollege(college);
        writeRecordData.setCurrentNum(writeRecords.size());
        writeRecordData.setEngagedNum(engagedNum);
        writeRecordData.setWorkNum(workNum);
        writeRecordData.setStudyNum(studyNum);
        writeRecordData.setCollegeNum(collegeNum);
        writeRecordData.setGraduateNum(graduateNum);
        writeRecordData.setDoctorNum(doctorNum);
        writeRecordData.setHometownNum(hometownNum);
        writeRecordData.setSalary0Num(salary0Num);
        writeRecordData.setSalary1Num(salary1Num);
        writeRecordData.setSalary2Num(salary2Num);
        writeRecordData.setSalary3Num(salary3Num);
        writeRecordData.setSalary4Num(salary4Num);

        writeRecordDataService.insert(writeRecordData);
    }
}
