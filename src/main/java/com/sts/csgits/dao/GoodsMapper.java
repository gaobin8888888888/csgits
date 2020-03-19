package com.sts.csgits.dao;

import com.sts.csgits.entity.Goods;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface GoodsMapper extends BaseMapper<Goods, Integer> {

    /**
     * 根据条件查询
     * @param goods
     * @return
     */
    public List<Goods> selectByGoods(Goods goods);

}
