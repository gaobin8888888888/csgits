package com.sts.csgits.service;

import com.sts.csgits.entity.Goods;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface GoodsService extends BaseService<Goods, Integer> {

    /**
     * 根据条件查询
     * @param goods
     * @return
     */
    public List<Goods> selectByGoods(Goods goods);
}
