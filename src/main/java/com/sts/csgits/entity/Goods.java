package com.sts.csgits.entity;

import lombok.Data;

import java.util.Date;

/**
 * 可兑换物品基本类
 * @author ：gb
 * @date ：Created in 2020/3/11 19:57
 */
@Data
public class Goods {

    private Integer id;

    //物品名
    private String name;

    //物品描述
    private String description;

    //图片路径
    private String imagePath;

    //剩余物品件数
    private Integer num;

    //所需积分数
    private Integer credits;

    //创建时间
    private String createTime;
}
