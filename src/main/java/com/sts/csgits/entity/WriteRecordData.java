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

    //记录时间
    private String date;

    //当前填写记录
    private Integer currentNum;

    //从事与本专业相关的工作或者学习记录数据
    private String relatedData;

    //不是从事与本专业相关的工作或者学习记录数据
    private String noRelatedData;

    private String createTime;
}
