package com.sts.csgits.dao;

import java.util.List;

/**
 * 公用的接口提取
 * @param <T>对应的实体类型
 * @author ：gb
 * @date ：Created in 2020/2/10 16:34
 */
public interface BaseMapper<T, ID> {

    /**
     * 添加记录，为null的属性设置为null
     *
     * @param record 要添加的记录信息
     * @return 根据需要返回
     */
    int insert(T record);

    /**
     * 按照Id删除指定记录
     * @param id 要删除指定Id
     * @return 删除影响的记录数量
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 更新记录，对于null的属性设置该属性为null
     *
     * @param record 记录的信息
     * @return 更新的数量
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据id查询记录
     *
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
