package com.sts.csgits.dao;

import com.sts.csgits.entity.Manager;
import com.sts.csgits.utils.Condition;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface ManagerMapper extends BaseMapper<Manager, Integer> {

    /**
     * 根据no和password查询管理员
     * @return
     */
    Manager selectByNoAndPassword(Manager manager);

    /**
     * 根据条件查询
     * @param manager
     * @return
     */
    List<Manager> selectByManager(Manager manager);

    /**
     * 根据条件查询条数
     * @param condition
     * @return
     */
    Integer selectNumByCondition(Condition condition);
}
