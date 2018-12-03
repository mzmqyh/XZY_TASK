package com.jnshu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.danga.MemCached.MemCachedClient;
import com.jnshu.entity.User;
import com.jnshu.myutils.*;
import com.jnshu.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    MemCachedClient memCachedClient;
    @Autowired
    SendCloudMailUtil sendCloudMailUtil;
    @Autowired
    AliyunOOSUtil aliyunOOSUtil;


    @ResponseBody
    @RequestMapping( value = "/sms/code", method = RequestMethod.POST )
    public Map smsCode(User user, HttpServletRequest request, HttpServletResponse response) throws ClientException {
        //logger.info("smsCode====="+user);
        System.out.println("\"/sms/code\"=====" + user);
        //判断手机号是否已经被注册
        Map <String, Object> map = new HashMap <String, Object>();
        if (userService.selectOne(user.getMobilephone()) != null) {
            logger.info("手机号已经被注册");
            map.put("num", "-101");
        } else {
            //调用第三方API发送验证码
            String phoneStr = String.valueOf(user.getMobilephone());
            String code = AliyunSendTemplateSMS.sendSms(phoneStr);
            System.out.println("code====" + code);
            logger.info("code===  " + code);
            //将验证码添加到缓存中，失效时间为5分钟。
            memCachedClient.set(phoneStr, code, new Date(1000 * 60 * 60 * 24));
            map.put("num", "200");
        }
        return map;
    }

    @RequestMapping( value = "register", method = RequestMethod.GET )
    public ModelAndView registerPage(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping( value = "/register", method = RequestMethod.POST )
    public ModelAndView register(User user, String smsCode, ModelAndView modelAndView) {
        logger.info("register=======\n" + "用户注册信息是：" + user + "验证码是：" + smsCode);
        //使用DES加密工具对密码进行加盐处理
        logger.info(user.getUsername() + user.getPassword());
        String phoneStr = String.valueOf(user.getMobilephone());
        String jspName = "register";
        //先判断验证码
        logger.info("从缓存中取到的验证码是：" + memCachedClient.get(phoneStr));
        if (smsCode.equals(memCachedClient.get(phoneStr))) {
            user.setPassword(MD5Util.md5(user.getUsername() + user.getPassword()));
            user.setCreateAt(System.currentTimeMillis());
            Boolean success = userService.register(user);
            try {
                if (!success) {
                    modelAndView.addObject("message", "用户名已存在");
                    //modelAndView.addObject("code", -600);
                    //jspName = "reRegister";
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                modelAndView.addObject("code", -601);
            }
            logger.info("注册成功");
            jspName = "registerSuccess";
        } else {
            logger.info("注册失败");
            modelAndView.addObject("message", "验证码错误");
        }
        modelAndView.setViewName(jspName);
        return modelAndView;
    }

    @RequestMapping( value = "login", method = RequestMethod.GET )
    public ModelAndView loginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping( value = "login", method = RequestMethod.POST )
    public String login(User user1, DESUtil desUtil, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("login=======" + user1);
        String view = " ";
        User user2 = userService.findByName(user1.getUsername());
        logger.info("user2======\n" + user2);
        if (user2 == null) {
            logger.info("用户不存在，请重新输入");
            view = "reLogin";
        } else if (user2.getPassword().equals(MD5Util.md5(user1.getUsername() + user1.getPassword()))) {
            //对cookie进行加密
            long time = System.currentTimeMillis();
            user2.setUpdateAt(time);
            String str1 = desUtil.encryptFromLong(time);
            String str2 = desUtil.encryptFromLong(user2.getId());
            //在相加时，用户名前后有“|”。这个是为了后面验证cookie时可以直接解密提出用户名。
            String token = desUtil.encrypt(str1 + "|" + user2.getUsername() + "|" + str2);
            Cookie nameCookie = new Cookie("name", user2.getUsername());
            nameCookie.setMaxAge(30 * 60);
            nameCookie.setPath("/");
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(30 * 60);
            tokenCookie.setPath("/");
            Cookie imgCookie = new Cookie("img", user2.getHeadPhoto());
            imgCookie.setMaxAge(30 * 60);
            imgCookie.setPath("/");
            response.addCookie(nameCookie);
            response.addCookie(tokenCookie);
            response.addCookie(imgCookie);
            view = "redirect:/u/task-93";
        } else {
            logger.info((MD5Util.md5(user1.getUsername() + user1.getPassword())));
            logger.info("登陆失败");
            view = "login";
        }
        return view;
    }

    @RequestMapping( value = "logout", method = RequestMethod.GET )
    public ModelAndView logout(HttpServletResponse response, HttpServletRequest request, ModelAndView modelAndView) {
        logger.info("logout==========");
        Cookie[] cookies = request.getCookies();
        if (cookies.length != 0) {
            logger.info("开始清理cookie..............");
            for (Cookie cookie : cookies) {
                logger.info("cookie.getValue()====" + cookie.getValue());
                if (cookie.getName().equals("name")) {
                    logger.info("cookie中有已登陆的用户");
                    cookie.setValue(null);
                    cookie.setMaxAge(0);//删除cookie就是将其生命周期设为0
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    logger.info("cookie已经成功删除");
                }
            }
        }
        modelAndView.setViewName("redirect:/task-91");
        return modelAndView;
    }

    @RequestMapping( value = "/email/register", method = RequestMethod.GET )
    public String emailresgister() {
        return "emailRegister";
    }

    @ResponseBody
    @RequestMapping( value = "/email/code", method = RequestMethod.POST )
    public Map getEmailCode(User user) throws IOException {
        logger.info("用户的邮箱" + user);
        Map <String, Object> map = new HashMap <String, Object>();
        if (userService.selectUser(user.getEmail()) != null) {
            logger.info("邮箱号已经被注册");
            map.put("num", "-101");
        } else {
            //调用邮箱接口，获取验证码
            String emailCode = sendCloudMailUtil.sendCloudSentEmail(user.getEmail());
            logger.info("code=======" + emailCode);
            //存到memcache邮箱为key,时常5分钟,为了方便写了24小时
            memCachedClient.set(user.getEmail(), emailCode, new Date(1000 * 60 * 60 * 24));
            map.put("num", "200");
        }
        return map;
    }

    @RequestMapping( value = "/email/register", method = RequestMethod.POST )
    public ModelAndView emailRegister(User user, String emailCode, ModelAndView modelAndView) {
        logger.info("email/egister=======\n" + "用户注册信息是：" + user + "验证码是：" + emailCode);
        //使用DES加密工具对密码进行加盐处理
        logger.info(user.getUsername() + user.getPassword());
        //String  = String.valueOf(user.getMobilephone());
        String jspName = "emailRegister";
        //先判断验证码
        logger.info("从缓存中取到的验证码是：" + memCachedClient.get(user.getEmail()));
        if (emailCode.equals(memCachedClient.get(user.getEmail()))) {
            user.setPassword(MD5Util.md5(user.getUsername() + user.getPassword()));
            user.setCreateAt(System.currentTimeMillis());
            Boolean success = userService.register(user);
            try {
                if (!success) {
                    modelAndView.addObject("message", "用户名已存在");
                    //modelAndView.addObject("code", -600);
                    //jspName = "reRegister";
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                modelAndView.addObject("code", -601);
            }
            logger.info("注册成功");
            jspName = "registerSuccess";
        } else {
            logger.info("注册失败");
            modelAndView.addObject("message", "验证码错误");
        }
        modelAndView.setViewName(jspName);
        return modelAndView;
    }

    @RequestMapping( value = "uploadFile", method = RequestMethod.GET )
    public String uploadFile() {
        logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$");
        return "uploadFile";
    }


    @RequestMapping( value = "uploadFile", method = RequestMethod.POST )
    public ModelAndView uploadFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String UPLOAD_DIRECTORY = "task7img/";
        logger.info("uploadFile====================" + file.getOriginalFilename());
        String filePath = "";
        if (file.getSize() > 10000000)
            return new ModelAndView("uploadFail", "msg", file.getOriginalFilename() + "超过了指定大小");
        filePath = aliyunOOSUtil.uploadImg(file, UPLOAD_DIRECTORY);
        logger.info("filePath========" + filePath);
        Cookie nameCookie = Cookies.getCookieByName(request, "name");
        logger.info("nameCookie==============" + nameCookie.getValue());
        Cookie imgCookie = Cookies.getCookieByName(request, "img");
        logger.info("imgCookie================" + imgCookie.getValue());
        // assert imgCookie != null;
        imgCookie.setValue(filePath);
        response.addCookie(imgCookie);
        //assert nameCookie != null;
        String name = nameCookie.getValue();

        User user = userService.findByName(name);
        logger.info("find by cookie ：" + user);
        user.setUpdateAt(System.currentTimeMillis());
        user.setHeadPhoto(filePath);
        userService.updateUserById(user);
        //成功跳转视图
        return new ModelAndView("uploadSuccess", "msg", file.getOriginalFilename());
    }
}
