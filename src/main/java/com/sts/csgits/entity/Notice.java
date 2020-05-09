package com.sts.csgits.entity;

import lombok.Data;

/**
 * 通知基本类
 * @author ：gb
 * @date ：Created in 2020/3/11 20:54
 */
@Data
public class Notice {

    private Integer id;

    //管理员id
    private Integer managerId;

    //通知标题
    private String title;

    //通知类型
    private Integer noticeType;

    //通知主体内容
    private String content;

    //创建时间
    private String createTime;

    private Manager manager;

}
