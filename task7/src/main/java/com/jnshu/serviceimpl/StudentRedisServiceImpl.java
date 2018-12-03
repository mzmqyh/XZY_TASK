package com.jnshu.serviceimpl;

import com.jnshu.entity.Student;
import com.jnshu.mapper.StudentMapper;
import com.jnshu.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service( "RedisImpl" )
public class StudentRedisServiceImpl implements StudentService {

    static final int FAILED = -1;
    static final int SUCCESS = 1;
    Logger logger = Logger.getLogger(StudentRedisServiceImpl.class);
    @Autowired
    StudentMapper student4Mapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public long CountSelective(String job, boolean state) {
        return 0;
    }

    @Override
    public long SelectCountByState(boolean state) {
        return 0;
    }

    @Override
    public List <Student> getOrderByKeyWords(Student student4) {
        return null;
    }

    @Override
    public List <Student> findAll() {
        return null;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Student findStudentById(Long id) {
        logger.info("findStudentById:" + id);
        String key = String.valueOf(id);
        Student student4 = (Student) redisTemplate.opsForValue().get("student4" + key);
        if (student4 != null) {
            logger.info("缓存中有：" + id);
            return student4;
        } else {
            logger.info("缓存中没有 ===" + student4Mapper.findById(id));
            student4 = student4Mapper.findById(id);
            boolean flag = false;
            try {
                redisTemplate.opsForValue().set("student4" + key, student4);
                flag = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            if (flag) {
                //添加成功
                return student4;
            } else {
                //添加失败
                return null;
            }
        }
    }

    @Override
    public Boolean batchSave(List <Student> student4List) {
        return null;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public long addStudent(Student student4) {
        logger.info("addStudent" + student4);
        //插入时返回
        int row = student4Mapper.insertSelective(student4);
        if (row != 0) {
            String key = String.valueOf(row);
            boolean flag = false;
            try {
                redisTemplate.opsForValue().set("student4" + key, student4);
                flag = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
                logger.info("set redis error" + e);
            }
            if (flag) {
                //添加成功
                return row;
            } else {
                //添加失败
                return FAILED;
            }
        } else {
            return FAILED;
        }
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public int updateStudent(Student student4) {
        logger.info("updateStudent" + student4);
        int row = student4Mapper.updateByPrimaryKey(student4);
        if (row != 0) {
            String key = String.valueOf(student4.getId());
            boolean flag = false;
            try {
                redisTemplate.opsForValue().set("student4" + key, student4);
                flag = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            if (flag) {
                //添加成功
                return SUCCESS;
            } else {
                //添加失败
                return FAILED;
            }
        } else {
            return FAILED;
        }
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public int deleteStudent(Student student4) {
        logger.info("deleteStudent" + student4);
        String key = String.valueOf(student4.getId());
        int row = student4Mapper.delete(student4);
        if (row != 0) {
            boolean flag = false;
            try {
                redisTemplate.delete("student4" + key);
                flag = true;
            } catch (RuntimeException e) {
                logger.info("delete redis failed" + e);
                e.printStackTrace();
            }
            if (flag) {
                //成功
                return SUCCESS;
            } else {
                //失败
                return FAILED;
            }
        } else {
            return FAILED;
        }
    }

    @Override
    public List <Student> getStudentByPage(int pageIndex, int pageSize) {
        return null;
    }
}
