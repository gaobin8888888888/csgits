package com.sts.csgits.service.impl;

import com.sts.csgits.dao.GoodsMapper;
import com.sts.csgits.entity.Goods;
import com.sts.csgits.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("goodsService")
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Integer> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public List<Goods> selectByGoods(Goods goods) {
        return goodsMapper.selectByGoods(goods);
    }
}
