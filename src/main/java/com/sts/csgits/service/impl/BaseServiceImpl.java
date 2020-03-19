package com.sts.csgits.service.impl;

import com.sts.csgits.dao.BaseMapper;
import com.sts.csgits.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/3/5 10:13
 */
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    @Autowired
    private BaseMapper<T, ID> baseMapper;

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Override
    public int deleteByPrimaryKey(ID id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    @Override
    public T selectByPrimaryKey(ID id) {
        return (T) baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }
}
