package com.jnshu.myutils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Cookies {
    /**
     * 根据cookieName返回该cookie
     *
     * @param request
     * @param cookieName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Cookie getCookieByName(HttpServletRequest request, String cookieName) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return new Cookie(cookie.getName(), URLDecoder.decode(cookie.getValue(), "UTF-8"));
            }
        }
        return null;
    }
  /*  public static void addCookie(String cookieName){
        Cookie nameCookie = new Cookie("name",cookieName);
        nameCookie.setMaxAge(30*60);
        nameCookie.setPath("/");
        response.addCookie(nameCookie);
    }*/
}
