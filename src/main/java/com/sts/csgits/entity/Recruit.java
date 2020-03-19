package com.sts.csgits.entity;

import lombok.Data;

/**
 * 企业招聘基本类
 * @author ：gb
 * @date ：Created in 2020/3/15 16:40
 */
@Data
public class Recruit {

    private Integer id;

    //企业名称
    private String name;

    //企业描述
    private String description;

    //招聘链接
    private String url;

    //添加企业的管理员id
    private Integer managerId;

    //招聘状态 0：招聘中 1：已结束
    private Integer status;

    private String createTime;

}
