package com.sts.csgits.service;

import java.util.List;

public interface BaseService<T, ID> {

    /**
     * 增加
     * @return
     */
    public int insert(T t);

    /**
     * 删除记录,ids如果是多个，中间用逗号分隔
     */
    public int deleteByPrimaryKey(ID id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据id查询记录
     * @param id 指定的id
     * @return 该id对应的记录
     */
    T selectByPrimaryKey(ID id);

    /**
     * 查询所有
     * @return
     */
    List<T> selectAll();

}
