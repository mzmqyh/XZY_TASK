package com.jnshu.service;

import com.jnshu.entity.Student;

import java.util.List;

public interface StudentService {
    long CountSelective(String job, boolean state);

    long SelectCountByState(boolean state);

    List <Student> getOrderByKeyWords(Student student);

    List <Student> findAll();

    Student findStudentById(Long id);

    Boolean batchSave(List <Student> studentList);

    public long addStudent(Student student);

    public int updateStudent(Student student);

    public int deleteStudent(Student student);

    public List <Student> getStudentByPage(int pageIndex, int pageSize);
}
