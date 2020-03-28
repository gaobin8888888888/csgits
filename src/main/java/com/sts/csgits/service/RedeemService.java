package com.sts.csgits.service;

import com.sts.csgits.entity.Redeem;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface RedeemService extends BaseService<Redeem, Integer> {

    /**
     * 根据属性查询
     * @param condition
     * @return
     */
    public List<Redeem> selectByCondition(Condition condition);
}
