package com.jnshu.myutils;

import java.util.UUID;

public class UUIDUtil {
    //随机生成数字
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
