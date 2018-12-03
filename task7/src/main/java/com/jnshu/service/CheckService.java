package com.jnshu.service;

import com.jnshu.entity.Check;

public interface CheckService {
    public int insert(Check check);

    public int countByPhone(String phone);

    public Check selectByPhone(String tel);

    public int countByEmail(String email);

    public Check selectByEmail(String email);
}
