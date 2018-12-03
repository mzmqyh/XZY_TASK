package com.jnshu.myutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
    public static final int MOBILE_NUMBER_LENGTH = 11;

    public static boolean isMobileNumber(String mobileNumber) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (mobileNumber.length() != MOBILE_NUMBER_LENGTH) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobileNumber);
            return m.matches();
        }
    }

    public static boolean isEmailAddress(String email) {
        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
