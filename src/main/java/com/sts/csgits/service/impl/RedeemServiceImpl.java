package com.sts.csgits.service.impl;

import com.sts.csgits.dao.RedeemMapper;
import com.sts.csgits.entity.Redeem;
import com.sts.csgits.service.RedeemService;
import com.sts.csgits.utils.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("redeemService")
public class RedeemServiceImpl extends BaseServiceImpl<Redeem, Integer> implements RedeemService {

    @Autowired
    private RedeemMapper redeemMapper;


    @Override
    public List<Redeem> selectByCondition(Condition condition) {
        return redeemMapper.selectByCondition(condition);
    }
}
