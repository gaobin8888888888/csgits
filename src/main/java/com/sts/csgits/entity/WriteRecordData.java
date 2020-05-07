package com.sts.csgits.entity;

import lombok.Data;

/**
 * 填写记录数据统计
 * @author ：gb
 * @date ：Created in 2020/5/5 11:47
 */
@Data
public class WriteRecordData {

    private Integer id;

    //创建记录id
    private Integer createRecordId;

    //学校id
    private Integer schoolId;

    //学院
    private String college;

    //记录时间
    private String date;

    //当前填写记录
    private Integer currentNum;

    //从事与本专业相关的工作或者学习
    private Integer engagedNum;

    //工作的人数
    private Integer workNum;

    //工作的人数
    private Integer studyNum;

    //大学生人数
    private Integer collegeNum;

    //研究生人数
    private Integer graduateNum;

    //博士人数
    private Integer doctorNum;

    //在家乡人数
    private Integer hometownNum;

    private Integer salary0Num;
    private Integer salary1Num;
    private Integer salary2Num;
    private Integer salary3Num;
    private Integer salary4Num;

    private String createTime;
}
