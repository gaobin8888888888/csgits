package com.sts.csgits.entity;

import lombok.Data;

/**
 * 物品兑换记录表
 * @author ：gb
 * @date ：Created in 2020/3/11 20:36
 */
@Data
public class Redeem {

    private Integer id;

    //学生唯一id
    private String stuId;

    //物品id
    private Integer goodsId;

    //创建时间
    private String createTime;

    private Goods goods;

}
