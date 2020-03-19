package com.sts.csgits.service;

import com.sts.csgits.entity.School;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface SchoolService extends BaseService<School, Integer> {

    /**
     * 根据学校名称模糊查询
     * @param schoolName
     * @return
     */
    public List<School> selectByName(String schoolName);

    /**
     * 获取最新添加的学校
     * @return
     */
    public School selectOne();
}
