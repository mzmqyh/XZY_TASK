package com.jnshu.controller;

import com.jnshu.entity.Profession;
import com.jnshu.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping( value = "profession" )
public class ProfessionController {
    @Autowired
    ProfessionService professionService;

    @RequestMapping( value = "/u/task-93", method = RequestMethod.POST )
    public ModelAndView thirdPage(ModelAndView modelAndView) {
        List <Profession> professionList;
        professionList = professionService.getOneByPrimaryKey(10);
        modelAndView.addObject("professionList", professionList);
        modelAndView.addObject("bodyname", "task-93");
        modelAndView.setViewName("myView1");
        return modelAndView;
    }
}
