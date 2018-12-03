package com.jnshu;

import com.aliyun.oss.OSSClient;
import com.jnshu.myutils.AliyunOOSUtil;
import com.jnshu.myutils.AntiChinUtil;
import com.jnshu.myutils.SendCloudMailUtil;
import com.jnshu.myutils.TencentOOSUtil;
import com.qcloud.cos.COSClient;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@ContextConfiguration( locations = "classpath:conf/applicationContext.xml" )
@RunWith( SpringJUnit4ClassRunner.class )
public class AliyunOOSUtilTest {
    Logger logger = Logger.getLogger(AliyunOOSUtilTest.class);
    @Autowired
    AliyunOOSUtil aliyunOOSUtil;
    @Autowired
    TencentOOSUtil tencentOOSUtil;
    @Autowired
    AntiChinUtil antiChinUtil;
    @Autowired
    SendCloudMailUtil sendCloudMailUtil;
    private String ossbucketName = "dreamhony";

    @Test
    public void testUploadByNetworkStream() {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        //测试通过网络流上传文件
        try {
            URL url = new URL("https://www.aliyun.com/");
            aliyunOOSUtil.uploadByNetworkStream(ossClient, url, ossbucketName, "test/aliyun.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

   /* @Test
    public void testUploadByInputStream(){
        //测试通过输入流上传文件
        try {
            InputStream inputStream = new FileInputStream(new File("D:/picture/4518.png"));
            aliyunOOSUtil.uploadByInputStream(ossClient, inputStream, bucketName, "test/a.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testUploadByFile() throws IOException {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        //测试通过file上传文件
        File file = new File("D:/picture/4518.png");
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        String path = "tset2/" + file.getName();
        System.out.println(aliyunOOSUtil.uploadByFile(ossClient, multipartFile, ossbucketName, path));
    }

    @Test
    public void testDeleteFile() {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        //测试根据key删除oss服务器上的文件
        aliyunOOSUtil.deleteFile(ossClient, ossbucketName, "test/a.jpg");
    }

    @Test
    public void testGetInputStreamByOSS() {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        //测试根据key获取服务器上的文件的输入流
        try {
            InputStream content = aliyunOOSUtil.getInputStreamByOSS(ossClient, ossbucketName, "test/a.jpg");
            if (content != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    System.out.println("\n" + line);
                }
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                content.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryAllObject() {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        //测试查询某个bucket里面的所有文件
        List <String> results;
        results = aliyunOOSUtil.queryAllObject(ossClient, ossbucketName);
        System.out.println(results);
    }


    @Test
    public void testossToCos() {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        COSClient cosClient = tencentOOSUtil.getCOSClient();
        String cosBucketName = tencentOOSUtil.cosbucketname;
        List <String> ossList = aliyunOOSUtil.getObjects(ossClient, ossbucketName);
        for (String ossKey : ossList) {
            String key = ossKey;
            String temPath = "D:\\myloadfile\\oostem\\";
            String path = temPath + key;
            logger.info("path================" + path + "\n");
            logger.info("下载结果" + aliyunOOSUtil.download(ossClient, ossbucketName, ossKey));
            File file = new File(path);
            logger.info("oosPath============:" + key + "\n");
            logger.info("迁移后的url:" + tencentOOSUtil.upload(cosClient, key, file, cosBucketName));
        }
    }

    @Test
    public void testAddWaterMark() throws IOException {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();
        aliyunOOSUtil.addWaterMark(ossClient, "water.jpg");
    }

    @Test
    public void testAntiChanin() {
        OSSClient ossClient = aliyunOOSUtil.getOSSClient();

        antiChinUtil.hotlinkProtection(ossClient, ossbucketName);
        antiChinUtil.getHotlinkProtection(ossClient, ossbucketName);
    }

    @Test
    public void testSendCloud() throws IOException {
        logger.info(sendCloudMailUtil.sendCloudSentEmail("qinyonghui@jnshu.com"));
    }
}
