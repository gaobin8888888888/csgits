package com.sts.csgits.service;

import com.sts.csgits.entity.Notice;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface NoticeService extends BaseService<Notice, Integer> {

    /**
     * 根据条件查询
     * @param notice
     * @return
     */
    public List<Notice> selectByNotice(Notice notice);
}
