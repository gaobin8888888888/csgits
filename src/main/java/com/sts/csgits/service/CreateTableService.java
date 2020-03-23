package com.sts.csgits.service;

public interface CreateTableService {

    /**
     * 为学生创建表
     * @param schoolId
     */
    public void createStudentTable(Integer schoolId);

    /**
     * 为填写记录建表
     */
    public void createWriteRecordTable(Integer createRecordId);

}
