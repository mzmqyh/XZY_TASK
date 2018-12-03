package com.jnshu.mapper;

import com.jnshu.entity.Check;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckMapper {
    int deleteById(Integer id);

    int insert(Check record);

    int insertSelective(Check record);

    Check selectById(Integer id);

    Check selectByPhone(String tel);

    Check selectByEmail(String email);

    int updateByIdSelective(Check record);

    int updateById(Check record);

    List <Check> selectAll();

    int countByPhone(String phone);

    int countByEmail(String email);
}
