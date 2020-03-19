package com.sts.csgits.service.impl;

import com.sts.csgits.dao.SchoolMapper;
import com.sts.csgits.entity.School;
import com.sts.csgits.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("schoolService")
public class SchoolServiceImpl extends BaseServiceImpl<School, Integer> implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public List<School> selectByName(String schoolName){
        return schoolMapper.selectByName(schoolName);
    }

    @Override
    public School selectOne(){
        return schoolMapper.selectOne();
    }
}
