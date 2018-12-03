package com.jnshu.mapper;

import com.jnshu.entity.Student;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentMapper extends Mapper <Student> {
    Student findById(Long id);

    long batchSave(List <Student> student4List);

    List <Student> getStudentByPage(int startRow, int endRow);
}