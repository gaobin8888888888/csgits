package com.sts.csgits.entity;

import lombok.Data;

import java.util.Date;

/**
 * 学校基本类
 * @author ：gb
 * @date ：Created in 2020/2/10 16:34
 */
@Data
public class School {

    private Integer id;

    //学校名称
    private String name;

    //学校描述
    private String description;

    //学校图片
    private String imagePath;

    //学校教师人数
    private Integer teacherNum = 0;

    //学校总人数
    private Integer totalNum = 0;

    //学校在校总人数
    private Integer schoolNum = 0;

    // 记录创建时间
    private String createTime;

    //学院字符串
    private String colleges;

    private String[] tags;
}