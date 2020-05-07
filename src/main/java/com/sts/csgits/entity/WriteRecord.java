package com.sts.csgits.entity;

import lombok.Data;

/**
 * 填写记录表
 * @author ：gb
 * @date ：Created in 2020/3/20 21:04
 */
@Data
public class WriteRecord {

    private Integer id;

    private Integer schoolId;

    //学生唯一标识
    private String sole;

    //是否是否从事与本专业相关的工作或者学习
    private Integer related;

    //目前从事类型
    private Integer type;

    //最高学位
    private Integer degree;

    //所处位置
    private String place;

    //是否在家乡
    private Integer home;

    private Integer salary;

    //备注
    private String comment;

    private String createTime;

    //创建记录id
    private Integer createRecordId;
}
