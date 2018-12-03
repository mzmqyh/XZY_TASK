package com.jnshu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/SpringMVC")
public class Test {
    @RequestMapping("/login")
    public String index(){
        System.out.println("helloworld");
      /*  mv.addAttribute("msg", "SpringMVC");ModelMap mv*/
        return "login";
    }
}
