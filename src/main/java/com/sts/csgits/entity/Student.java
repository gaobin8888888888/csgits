package com.sts.csgits.entity;

import lombok.Data;

/**
 * 学生基本类
 * @author ：gb
 * @date ：Created in 2020/3/15 22:47
 */
@Data
public class Student {

    private Integer id;

    //学生唯一id
    private String sole;

    //学号
    private String no;

    //真实姓名
    private String realName;

    //密码
    private String password;

    //头像地址
    private String imagePath;

    //学校id
    private Integer schoolId;

    //学院
    private String college;

    //班级
    private String classNo;

    //是否优秀学生
    private Boolean fine;

    private String description;

    //积分数
    private Integer credits;

    //年级
    private Integer grade;

    //成绩
    private String achievement;

    //是否毕业
    private Boolean graduate;

    private String createTime;

}
