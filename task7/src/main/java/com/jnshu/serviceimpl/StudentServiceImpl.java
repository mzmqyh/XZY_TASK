package com.jnshu.serviceimpl;

import com.jnshu.entity.Student;
import com.jnshu.mapper.StudentMapper;
import com.jnshu.service.StudentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service( "NoCache" )
public class StudentServiceImpl implements StudentService {
    Logger logger = LogManager.getLogger(StudentServiceImpl.class.getName());
    @Autowired
    StudentMapper studentMapper;

    @Override
    public long CountSelective(String job, boolean state) {
        logger.info("CountSelective======\n" + "job=====" + job);
        long count = 0;
        Student student = new Student();
        student.setPosition(job);
        student.setState(state);
        count = studentMapper.selectCount(student);
        return count;
    }

    @Override
    public long SelectCountByState(boolean state) {
        logger.info("CountSelective======\n" + "state=====\n" + state);
        //通用Example查询
        Example example = new Example(Student.class);// 创建Example
        long count;
        //创建Criteria
        Example.Criteria criteria = example.createCriteria();
        //按工资升序排列
        //状态为false的是已毕业学员
        criteria.andEqualTo("state", state);
        count = studentMapper.selectCountByExample(example);
        return count;
    }

    @Override
    public List <Student> getOrderByKeyWords(Student student) {
        logger.info("CountSelective======\n" + "student=====\n" + student.toString());
        //String keyWords = student.getSalary();
        //通用Example查询
        Example example = new Example(Student.class);// 创建Example
        //创建Criteria
        Example.Criteria criteria = example.createCriteria();
        //按工资升序排列
        example.setOrderByClause("salary DESC");
        //状态为false的是已毕业学员
        criteria.andEqualTo("state", student.getState());
        List <Student> studentList = studentMapper.selectByExample(example);
        return studentList;
    }

    @Override
    public List <Student> findAll() {
        return studentMapper.selectAll();
    }

    @Override
    public Student findStudentById(Long id) {
        return studentMapper.findById(id);
        // return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean batchSave(List <Student> studentList) {
        logger.info("batchSave=======" + studentList);
        boolean flag = false;
        Long uid = studentMapper.batchSave(studentList);
        if (uid != 0) {
            logger.info("新增数量：=======" + uid);
            flag = true;
        }
        return flag;
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
        return studentMapper.deleteByExample(student);
    }

    @Override
    public List <Student> getStudentByPage(int pageIndex, int pageSize) {
        return null;
    }

}

