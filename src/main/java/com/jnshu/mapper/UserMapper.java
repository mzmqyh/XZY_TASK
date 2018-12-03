package com.jnshu.mapper;

import com.jnshu.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper <User> {
    public Long addOne(User user);

    public User findOne(User user);

    public User findByName(String username);

    public int insert(User user);

    public int update(User user);

    //下面两个接口是给任务七写的
    public long addUser(User user);

    public User selectUser(String username);

    //public User selectOne(User record);
    public Boolean updateUserById(User user);
}