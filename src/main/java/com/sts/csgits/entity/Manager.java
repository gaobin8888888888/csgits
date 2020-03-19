package com.sts.csgits.entity;

import lombok.Data;

import java.util.Date;

/**
 * 管理员信息基本类
 * @author ：gb
 * @date ：Created in 2020/3/1 16:32
 */
@Data
public class Manager {

    private Integer id;

    //老师编号
    private String no;

    //真实姓名
    private String realName;

    //密码
    private String password;

    //头像地址
    private String imagePath;

    //联系方式
    private String tel;

    //学校id
    private Integer schoolId;

    //学校
    private School school;

    //学院
    private String college;

    //是否优秀教师
    private Boolean fine;

    //个人描述
    private String description;

    //是否被限制
    private Boolean confined;

    //创建时间
    private String createTime;
}
