package com.jnshu.controller;

import com.jnshu.entity.Profession;
import com.jnshu.entity.Student;
import com.jnshu.service.ProfessionService;
import com.jnshu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    ProfessionService professionService;
    @Qualifier( "NoCache" )
    @Autowired
    StudentService studentService;

    @RequestMapping( value = "task-91", method = RequestMethod.GET )
    public ModelAndView firstPage(ModelAndView modelAndView) {
        Student student = new Student();
        student.setState(false);
        List <Student> student4List = studentService.getOrderByKeyWords(student);
        long onlinCount = studentService.SelectCountByState(true);
        long graduateCount = studentService.SelectCountByState(false);
        modelAndView.addObject("onlinCount", onlinCount);
        modelAndView.addObject("graduateCount", graduateCount);
        modelAndView.addObject("student4List", student4List);
        modelAndView.addObject("bodyname", "task-91");
        modelAndView.setViewName("myView1");
        return modelAndView;
    }

    @RequestMapping( value = "task-92", method = RequestMethod.GET )
    public ModelAndView secondPage(ModelAndView modelAndView) {
        //modelAndView.addObject(, );
        modelAndView.addObject("bodyname", "task-92");
        modelAndView.setViewName("myView1");
        return modelAndView;
    }

    @RequestMapping( value = "/u/task-93", method = RequestMethod.GET )
    public ModelAndView thirdPage(ModelAndView modelAndView) {
        List <Profession> professionList;
        long count = 0;
        //Student student = new Student();
        professionList = professionService.getOneByPrimaryKey(10);
           /* student.setPosition("前端工程师");
            student.setState(true);*/
        count = studentService.CountSelective("前端工程师", false);
        modelAndView.addObject("count", count);
        modelAndView.addObject("professionList", professionList);
        modelAndView.addObject("bodyname", "task-93");
        modelAndView.setViewName("myView1");
        return modelAndView;
    }
}

