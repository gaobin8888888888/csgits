package com.sts.csgits.dao;

import com.sts.csgits.entity.Redeem;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * 兑换记录
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface RedeemMapper extends BaseMapper<Redeem, Integer> {

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    public List<Redeem> selectByCondition(Condition condition);

}
