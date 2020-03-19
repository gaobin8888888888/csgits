package com.sts.csgits.service.impl;

import com.sts.csgits.dao.NoticeMapper;
import com.sts.csgits.entity.Notice;
import com.sts.csgits.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceImpl<Notice, Integer> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public List<Notice> selectByNotice(Notice notice) {
        return noticeMapper.selectByNotice(notice);
    }
}
