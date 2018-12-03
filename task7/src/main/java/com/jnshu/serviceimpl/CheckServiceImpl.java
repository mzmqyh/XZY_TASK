package com.jnshu.serviceimpl;

import com.jnshu.entity.Check;
import com.jnshu.mapper.CheckMapper;
import com.jnshu.service.CheckService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;

public class CheckServiceImpl implements CheckService {
    Logger logger = Logger.getLogger(StudentMemcacheServiceImpl.class);
    @Resource
    private CheckMapper checkMapper;

    @Override
    public int insert(Check record) {
        int result = checkMapper.insert(record);
        return result;
    }

    @Override
    public int countByPhone(String tel) {
        int result = checkMapper.countByPhone(tel);
        return result;
    }

    @Override
    public Check selectByPhone(String tel) {
        Check check = checkMapper.selectByPhone(tel);
        return check;
    }

    @Override
    public int countByEmail(String email) {

        return checkMapper.countByEmail(email);
    }

    @Override
    public Check selectByEmail(String email) {
        Check check = checkMapper.selectByEmail(email);
        return check;
    }
}
