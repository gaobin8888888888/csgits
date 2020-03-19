package com.sts.csgits.service;

import com.sts.csgits.entity.Manager;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface ManagerService extends BaseService<Manager, Integer> {

    /**
     * 根据no和用户名查询
     * @param manager
     * @return
     */
    public Manager selectByNoAndPassword(Manager manager);

    /**
     * 根据条件查询
     * @param manager
     * @return
     */
    public List<Manager> selectByManager(Manager manager);
}
