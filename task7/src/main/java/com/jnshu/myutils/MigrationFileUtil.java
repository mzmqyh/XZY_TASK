package com.jnshu.myutils;

import com.aliyun.oss.OSSClient;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObjectSummary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * @ClassName MigrationFile
 * @Auther: QYH
 * @Date: 2018/11/30 22:47
 * @Description: TODO
 * @Version 1.0
 */
public class MigrationFileUtil {
    @Autowired
    TencentOOSUtil tencentOOSUtil;
    @Autowired
    AliyunOOSUtil aliyunOOSUtil;

    public void cosToOOS(COSClient cosClient, OSSClient oosClient, String cosbucketName, String oosbucketname) throws IOException {
        Logger logger = Logger.getLogger(TencentOOSUtil.class);
        String temPath = "D://myloadfile/";
        logger.info("uri============:" + cosbucketName);
        List <COSObjectSummary> objects = tencentOOSUtil.listObject(cosClient, cosbucketName);
        for (COSObjectSummary object : objects) {
            logger.info("******************" + object.getStorageClass() + "\n");
            String key = object.getKey();
            String path = temPath + key;
            logger.info("path================" + path + "\n");
            logger.info("下载结果" + tencentOOSUtil.download(cosClient, path, key, cosbucketName));
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            logger.info("oosPath" + key);
            logger.info("迁移后的url" + aliyunOOSUtil.uploadByFile(oosClient, multipartFile, oosbucketname, key));
        }
    }

//    public static void oosToCos(COSClient cosClient, OSSClient oosClient, String cosbucketName,String oosbucketname){
//
//    }
//
}
