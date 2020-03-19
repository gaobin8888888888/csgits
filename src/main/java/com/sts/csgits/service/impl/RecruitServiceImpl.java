package com.sts.csgits.service.impl;

import com.sts.csgits.dao.RecruitMapper;
import com.sts.csgits.entity.Recruit;
import com.sts.csgits.service.RecruitService;
import com.sts.csgits.utils.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("recruitService")
public class RecruitServiceImpl extends BaseServiceImpl<Recruit, Integer> implements RecruitService {

    @Autowired
    private RecruitMapper recruitMapper;

    @Override
    public List<Recruit> selectByName(String recruitName){
        return recruitMapper.selectByName(recruitName);
    }

    @Override
    public List<Recruit> selectByCondition(Condition condition){
        return recruitMapper.selectByCondition(condition);
    }
}
