package com.sts.csgits.dao;

import com.sts.csgits.entity.Recruit;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * 企业招聘
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface RecruitMapper extends BaseMapper<Recruit, Integer> {

    /**
     * 根据企业名称模糊查询
     * @param recruitName
     * @return
     */
    public List<Recruit> selectByName(String recruitName);

    /**
     * 管理员查找自己添加的企业招聘信息
     * @param condition
     * @return
     */
    public List<Recruit> selectByCondition(Condition condition);

}
