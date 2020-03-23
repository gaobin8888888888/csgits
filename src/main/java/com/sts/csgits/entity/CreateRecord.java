package com.sts.csgits.entity;

import lombok.Data;

/**
 * 教师创建填写记录表
 * @author ：gb
 * @date ：Created in 2020/3/20 20:51
 */
@Data
public class CreateRecord {

    private Integer id;

    //记录名称
    private String name;

    //记录描述
    private String description;

    //管理员id
    private Integer managerId;

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    //创建时间
    private String createTime;
}
