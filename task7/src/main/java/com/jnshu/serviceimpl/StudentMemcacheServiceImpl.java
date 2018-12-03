package com.jnshu.serviceimpl;

import com.danga.MemCached.MemCachedClient;
import com.jnshu.entity.Student;
import com.jnshu.mapper.StudentMapper;
import com.jnshu.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service( "MemcacheImpl" )
public class StudentMemcacheServiceImpl implements StudentService {

    Logger logger = Logger.getLogger(StudentMemcacheServiceImpl.class);
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    MemCachedClient memCachedClient;

    @Override
    public long CountSelective(String job, boolean state) {
        return 0;
    }

    @Override
    public long SelectCountByState(boolean state) {
        return 0;
    }

    @Override
    public List <Student> getOrderByKeyWords(Student student) {
        return null;
    }

    @Override
    public List <Student> findAll() {
        return null;
    }

    @Override
    public Student findStudentById(Long id) {
        logger.info("findStudentById:" + id);
        String key = String.valueOf(id);
        Student student = (Student) memCachedClient.get("student" + key);
        if (student != null) {
            logger.info("缓存中有：" + id);
            return student;
        } else {
            logger.info("缓存中没有 ===" + studentMapper.findById(id));
            student = studentMapper.findById(id);
            boolean success = memCachedClient.set("student" + key, student);
            if (success) {
                return student;
            } else {
                //设置失败
                return null;
            }
        }
    }

    @Override
    public Boolean batchSave(List <Student> studentList) {
        return null;
    }

    @Override
    public long addStudent(Student student) {
        return 0;
    }

    @Override
    public int updateStudent(Student student) {
        return 0;
    }

    @Override
    public int deleteStudent(Student student) {
        return 0;
    }

    @Override
    public List <Student> getStudentByPage(int pageIndex, int pageSize) {
        return null;
    }
}
