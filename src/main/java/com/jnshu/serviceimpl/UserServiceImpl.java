package com.jnshu.serviceimpl;

import com.jnshu.entity.User;
import com.jnshu.mapper.UserMapper;
import com.jnshu.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    @Autowired
    UserMapper userMapper;

    @Override
    public Boolean register(User user) {
        logger.info("register=======" + user.toString());
        boolean flag = false;
        int uid = userMapper.insertSelective(user);
        if (uid != 0) {
            logger.info("uid=======" + uid);
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean login(User user) {
        logger.info("login=======" + user);
        Boolean flag = false;
        logger.info(userMapper.findOne(user));
        if (userMapper.findOne(user) != null) {
            flag = true;
        }
        return flag;
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey((Object) id);
    }

    @Override
    public int updateByIdSelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateById(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int countByName(String name) {
        return userMapper.selectCountByExample((Object) name);
    }

    @Override
    public long addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User selectUser(String username) {
        return userMapper.selectUser(username);
    }

    @Override
    public User selectOne(long mobilephone) {
        User user = new User();
        user.setMobilephone(mobilephone);
        return userMapper.selectOne(user);
    }

    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public boolean updateUserById(User user) {
        return userMapper.updateUserById(user);
    }
}
