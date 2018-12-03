package com.jnshu.myutils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketReferer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AntiChinUtil
 * @Auther: QYH
 * @Date: 2018/12/1 23:27
 * @Description: TODO
 * @Version 1.0
 */
@Component
public class AntiChinUtil {
    public void hotlinkProtection(OSSClient ossClient, String bucketName) {
        List <String> refererList = new ArrayList <String>();
// 添加Referer白名单。Referer参数支持通配符星号（*）和问号（？）。
        refererList.add("127.0.0.1");
        refererList.add("106.15.203.211");
        refererList.add("*.console.aliyun.com");
// 设置存储空间Referer列表。设为true表示Referer字段允许为空。
        BucketReferer br = new BucketReferer(true, refererList);
        ossClient.setBucketReferer(bucketName, br);
// 关闭OSSClient。
//        ossClient.shutdown();
    }

    public void getHotlinkProtection(OSSClient ossClient, String bucketName) {
        // 获取存储空间Referer白名单列表。
        BucketReferer br = ossClient.getBucketReferer(bucketName);
        List <String> refererList = br.getRefererList();
        for (String referer : refererList) {
            System.out.println(referer);
        }

//        ossClient.shutdown();
    }

    public void deleteHotlinkProtection(OSSClient ossClient, String bucketName) {
// 防盗链不能直接清空，需要新建一个允许空Referer的规则来覆盖之前的规则。
        BucketReferer br = new BucketReferer();
        ossClient.setBucketReferer(bucketName, br);
//    ossClient.shutdown();
    }
}
