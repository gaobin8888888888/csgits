package com.sts.csgits.service.impl;

import com.sts.csgits.dao.StudentMapper;
import com.sts.csgits.service.CreateTableService;
import com.sts.csgits.utils.ReadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

/**
 * 创建数据库表
 */
@Service("createTableService")
@Slf4j
public class CreateTableServiceImpl implements CreateTableService {

    @Autowired
    private StudentMapper studentMapper;

    private static final String TABLE_STUDENT = "student_";
    private static final String TABLE_WRITE_RECORD = "write_record_";

    private static final String TABLE_STUDENT_REPLACE = "student_template";
    private static final String TABLE_WRITE_RECORD_REPLACE = "write_record_template";

    private static final String URL_TABLE_STUDENT = "/sql/student.sql";
    private static final String URL_TABLE_WRITE_RECORD = "/sql/write_record.sql";

    @Override
    public void createStudentTable(Integer schoolId) {
        try {
            log.info("为学生" + schoolId + "创建表开始,当前时间:" + new Date());
            String tableName = TABLE_STUDENT + schoolId;
            int count = studentMapper.isExistsTable(tableName);
            if (count > 0) {
                log.info("表" + tableName + "已经存在！");
            } else {
                URL url = CreateTableServiceImpl.class.getResource(URL_TABLE_STUDENT);
                String createTabSql = ReadFileUtil.readFile(url.getPath()).replace(TABLE_STUDENT_REPLACE, tableName);
                studentMapper.executeSql(createTabSql);
            }
        } catch (Exception e) {
            log.error("CrontabServiceImpl createStudentTable error:{}", e);
        }
        log.info("为学生" + schoolId + "创建表结束,当前时间:" + new Date());
    }

    @Override
    public void createWriteRecordTable(Integer createRecordId) {
        try {
            log.info("为填写记录" + createRecordId + "创建表开始,当前时间:" + new Date());
            String tableName = TABLE_WRITE_RECORD + createRecordId;
            int count = studentMapper.isExistsTable(tableName);
            if (count > 0) {
                log.info("表" + tableName + "已经存在！");
            } else {
                URL url = CreateTableServiceImpl.class.getResource(URL_TABLE_WRITE_RECORD);
                String createTabSql = ReadFileUtil.readFile(url.getPath()).replace(TABLE_WRITE_RECORD_REPLACE, tableName);
                studentMapper.executeSql(createTabSql);
            }
        } catch (Exception e) {
            log.error("CrontabServiceImpl createWriteRecordTable error:{}", e);
        }
        log.info("为填写记录" + createRecordId + "创建表结束,当前时间:" + new Date());
    }
}
