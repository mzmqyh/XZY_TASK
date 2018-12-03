package com.jnshu.controller;

import com.jnshu.entity.Check;
import com.jnshu.myutils.MD5Util;
import com.jnshu.myutils.UUIDUtil;
import com.jnshu.serviceimpl.CheckServiceImpl;
import com.jnshu.serviceimpl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;

public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    private Cookie cookie;
    @Resource
    private CheckServiceImpl checkServiceImpl;
    @Resource
    private StudentServiceImpl studentServiceImpl;
    private static String code = "";
    private static long timeBgein = 0;


    //首页：用户看到的第一页，着重于测试功能
    @RequestMapping( value = "/", method = RequestMethod.GET )
    public String home() {
        return "login";
    }

    //登陆页面，附带三个注册链接，跳转向不同的注册页面
//       @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(HttpServletResponse response, HttpServletRequest request,
//                        @RequestParam("username") String username, @RequestParam("password") String password,
//                        Model model) throws UnsupportedEncodingException {
//        Check checkPhone = checkServiceImpl.selectByPhone(username);
//        Check checkEmail = checkServiceImpl.selectByEmail(username);
//        if(checkPhone == null&& checkPhone == checkEmail){
//            model.addAttribute("result", "用户名不存在，请输入正确的手机号或者邮箱");
//            return "login";
//        }else{
//         Check check = null;
//         if(checkEmail == null){
//          check = checkPhone;
//         }else{
//             check = checkEmail;
//            }
//            String md5 = MD5Util.md5(password+check.getSalt());
//            String s = password+ check.getSalt();
//            logger.info("MD5Util.MD5(s):\t" + MD5Util.md5(s));
//            if(!check.getMd5().equals(MD5Util.md5(s))){
//                return "login";
//            }else{
//                //设置日期格式
//                String df = System.currentTimeMillis()+"";
//                String tokenz = username +","+df;
//                String token = DESUtil.encrypt(tokenz);
//                Cookie cookie = new Cookie("name",token);
//                cookie.setMaxAge(60*60*24*30);
//                response.addCookie(cookie);
//                model.addAttribute("result", 200);
//                return "redirect:/students";
//            }
//        }
//    }

    //todo 手机号码输入页客户前往手机页面
    @RequestMapping( value = "/phone", method = RequestMethod.GET )
    public String phone() {
        return "phone";
    }

    //todo 邮箱输入页客户前往邮箱页面
    @RequestMapping( value = "/email", method = RequestMethod.GET )
    public String email() {
        return "email";
    }

    // 验证码,用户注册页
    // 客户手机验证码页面 此处发送验证码
    @RequestMapping( value = "/phoneSend", method = RequestMethod.POST )
    public String phoneSend(@RequestParam( "phone" ) String phone, Model model) throws Exception {
        if (!phone.equals("")) {
            int a = checkServiceImpl.countByPhone(phone);
            if (a == 1) {
                model.addAttribute("result", "您的手机号已经注册，请直接登录！");
                return "login";
            } else {
                code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                //MoblieMessageUtil.sendSms(phone, code).getCode();
                model.addAttribute("result", "您的验证码为：{}" + code);
                logger.info("发送的验证码为：\t" + code);
                return "registerP";
            }
        } else {
            model.addAttribute("result", "获取验证码失败，请重新输入手机号码！");
            return "phone";
        }
    }

    //todo 客户正式注册页面
    //todo 客户手机注册页面
    @RequestMapping( value = "/registerP", method = RequestMethod.POST )
    public String PRegister(Model model, @RequestParam( "username" ) String username, @RequestParam( "phone" ) String phone, @RequestParam( "password" ) String password, @RequestParam( "check" ) String check) throws UnsupportedEncodingException {
        if (!(username.equals("") && phone.equals("") && password.equals("") && check.equals(""))) {
            logger.info("name:" + username + "\t" + "phone:" + phone + "\t" + "password" + "\t" + password + "\t" + "check" + "\t" + check);
            int a = checkServiceImpl.countByPhone(phone);
            logger.info("checkServiceImpl.countByPhone(phone):" + a + "\t" + "CODE" + "\t" + code);
            if (a == 0) {
                String salt = UUIDUtil.getUUID();
                String bfMD5 = password + salt;
                String md5 = MD5Util.md5(bfMD5);
                logger.info("MD5:" + "\t" + md5 + "salt" + "\t" + salt);
                Check phoneRegisterCheck = new Check();
                phoneRegisterCheck.setTel(phone);
                phoneRegisterCheck.setEmail(null);
                phoneRegisterCheck.setTocheck(code);
                phoneRegisterCheck.setMd5(md5);
                phoneRegisterCheck.setSalt(salt);
                phoneRegisterCheck.setStates(1);
                phoneRegisterCheck.setCreateat(System.currentTimeMillis());
                phoneRegisterCheck.setUpdateat(System.currentTimeMillis());
                int b = checkServiceImpl.insert(phoneRegisterCheck);
                if (b == 1) {
                    model.addAttribute("result", "您的手机号已经注册成功！");
                    return "record";
                }
            } else {
                model.addAttribute("result", "您的手机号已经注册！");
                return "Login";
            }
        }
        return "registerP";
    }
}
