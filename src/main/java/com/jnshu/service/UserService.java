package com.jnshu.service;

import com.jnshu.entity.User;

public interface UserService {
    public Boolean register(User user);

    public Boolean login(User user);

    int deleteById(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectById(Integer id);

    int updateByIdSelective(User record);

    int updateById(User record);

    int countByName(String name);

    //下面两个接口是给任务七写的
    long addUser(User user);

    User selectUser(String username);

    User selectOne(long mobilephone);

    User findByName(String username);

    boolean updateUserById(User user);
}
