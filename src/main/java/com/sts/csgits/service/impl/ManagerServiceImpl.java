package com.sts.csgits.service.impl;

import com.sts.csgits.dao.ManagerMapper;
import com.sts.csgits.dao.SchoolMapper;
import com.sts.csgits.entity.Manager;
import com.sts.csgits.entity.School;
import com.sts.csgits.service.ManagerService;
import com.sts.csgits.service.SchoolService;
import com.sts.csgits.utils.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("managerService")
public class ManagerServiceImpl extends BaseServiceImpl<Manager, Integer> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager selectByNoAndPassword(Manager manager) {
        return managerMapper.selectByNoAndPassword(manager);
    }

    @Override
    public List<Manager> selectByManager(Manager manager){
        return managerMapper.selectByManager(manager);
    }

    @Override
    public List<Manager> selectByCondition(Condition condition){
        return managerMapper.selectByCondition(condition);
    }
}
