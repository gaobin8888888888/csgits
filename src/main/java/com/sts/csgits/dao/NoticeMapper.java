package com.sts.csgits.dao;

import com.sts.csgits.entity.Notice;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface NoticeMapper extends BaseMapper<Notice, Integer> {

    /**
     * 根据条件查询
     * @param notice
     * @return
     */
    public List<Notice> selectByNotice(Notice notice);

}
