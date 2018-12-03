package com.jnshu.myutils;


import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aliyun.oss.OSSClient;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

@Component
public class AliyunOOSUtil {
    private Logger logger = Logger.getLogger(AliyunOOSUtil.class);
    public String bucketName = "dreamhony";

    /**
     * @return OSSClient oss客户端
     * @throws
     * @Title: getOSSClient
     * @Description: 获取oss客户端
     */
    public OSSClient getOSSClient() {
        //使用你的对应的endpoint地址
        String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIkXEF6StJzWLo";
        String accessKeySecret = "lk5Clyyo27XkwZZCefOkRfcK0GAiXH";
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

  /*  //上传图片
    public URL uploadImg(String key, byte[] img) {
        logger.info("Started");
        URL url =null;
        try {
            InputStream is = new ByteArrayInputStream(img);
            ossClient.putObject(bucketName, key, is);
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);// 生成 URL
            url = ossClient.generatePresignedUrl(bucketName, key, expiration);
            logger.info("Object：" + key + "存入OSS成功。");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Completed");
        return url;
    }
    //删除图片
    public  boolean deleteImg(String key) throws ClientException {
        logger.info("Started");
        boolean flag = false;
        try {
            ossClient.deleteObject(bucketName, key);
            System.out.println("删除Object：" + key + "成功。");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Completed");
        return flag;
    }
    //下载图片
    public  boolean downloadImg(String key) {
        logger.info("Started");
        boolean flag = false;
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        try {
            InputStream inputStream = ossObject.getObjectContent();
            StringBuilder objectContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                objectContent.append(line);
            }
            inputStream.close();
            System.out.println("Object：" + key + "的内容是：" + objectContent);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Completed");
        return flag;
    }
*/

    /**
     * @param ossClient  oss客户端
     * @param url        URL
     * @param bucketName bucket名称
     * @param objectName 上传文件目录和（包括文件名）例如“test/index.html”
     * @return void        返回类型
     * @throws
     * @Title: uploadByNetworkStream
     * @Description: 通过网络流上传文件
     */
    public void uploadByNetworkStream(OSSClient ossClient, URL url, String bucketName, String objectName) {
        try {
            InputStream inputStream = url.openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * @param ossClient   oss客户端
     * @param inputStream 输入流
     * @param bucketName  bucket名称
     * @param objectName  上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void        返回类型
     * @throws
     * @Title: uploadByInputStream
     * @Description: 通过输入流上传文件
     */
    public void uploadByInputStream(OSSClient ossClient, InputStream inputStream, String bucketName, String objectName) {
        try {
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                //ossClient.shutdown();
            }
        }
    }

    /**
     * @param ossClient  oss客户端
     * @param file       上传的文件
     * @param bucketName bucket名称
     * @param objectName 上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void        返回类型
     * @throws
     * @Title: uploadByFile
     * @Description: 通过file上传文件
     */
    public URL uploadByFile(OSSClient ossClient, MultipartFile file, String bucketName, String objectName) {
        URL url = null;
        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
            Date expiration = new Date(new Date().getTime() + 1000 * 3600 * 24 * 10);
            url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                //ossClient.shutdown();
            }
        }
        return url;
    }

    /**
     * @param ossClient  oss客户端
     * @param bucketName bucket名称
     * @param key        文件路径/名称，例如“test/a.txt”
     * @return void            返回类型
     * @throws
     * @Title: deleteFile
     * @Description: 根据key删除oss服务器上的文件
     */
    public void deleteFile(OSSClient ossClient, String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }

    /**
     * @param ossClient  oss客户端
     * @param bucketName bucket名称
     * @param key        文件路径和名称
     * @return InputStream    文件输入流
     * @throws
     * @Title: getInputStreamByOSS
     * @Description:根据key获取服务器上的文件的输入流
     */
    public InputStream getInputStreamByOSS(OSSClient ossClient, String bucketName, String key) {
        InputStream content = null;
        try {
            OSSObject ossObj = ossClient.getObject(bucketName, key);
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private String getSubStr(String str, int num) {
        String result = "";
        int i = 0;
        while (i < num) {
            int lastFirst = str.lastIndexOf('/');
            result = str.substring(lastFirst) + result;
            str = str.substring(0, lastFirst);
            i++;
        }
        return result.substring(1);
    }

    /**
     * @param ossClient     oss客户端
     * @param oosBucketName bucket名称
     * @return List<String>  文件路径和大小集合
     * @throws
     * @Title: queryAllObject
     * @Description: 查询某个bucket里面的所有文件
     */

    public boolean download(OSSClient ossClient, String oosBucketName, String key) {
        logger.info("download=============\n" + "oosBucketName:" + oosBucketName + "\nkey" + key);
        String temPath = "D:\\myloadfile\\oostem\\" + key;
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(oosBucketName, key);
            File file = new File(temPath);
            String fileName = getSubStr(temPath, 1);
            logger.info("*************************fileName:" + fileName + "\n");
            //获取templpath最后一个"\"之前的内容；把temPath中的的str1用空格取代
            String path = temPath.replaceAll(fileName, "");
            logger.info("**************************path:" + path + "\n");
            //生成不存在的路径
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            ossClient.getObject(getObjectRequest, file);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List <String> queryAllObject(OSSClient ossClient, String bucketName) {
        List <String> results = new ArrayList <String>();
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                results.add(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return results;
    }

    /**
     * 列举文件
     *
     * @return
     */
    public List <String> getObjects(OSSClient ossClient, String ossBucketName) {
        List <String> results = new ArrayList <String>();
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(ossBucketName));
            //获取所有文件的描述信息。
            for (OSSObjectSummary ossObjectSummary : objectListing.getObjectSummaries()) {
                results.add(ossObjectSummary.getKey());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                //ossClient.shutdown();
            }
        }
        return results;
    }

    //添加水印
    public void addWaterMark(OSSClient ossClient, String objectName) throws IOException {
        // 缩放
        String style = "image/auto-orient,1/quality,q_90/watermark,text_aGVsbG8gd29ybGQ,size_40,x_10,y_10";
        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("water.jpg"));
        // 水印
        style = "image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-watermark.jpg"));
    }

    //上传图片
    public String uploadImg(MultipartFile multipartFile, String directory) throws IOException {
        logger.info("uploadImg=======" + "file: " + multipartFile.getOriginalFilename() + "\n" + "directory:" + directory);
        OSSClient ossClient = getOSSClient();
        String url;
        //directory:要上传到阿里云的什么位置
        logger.info("*******************Started*********************");

        // File file = new File("D:/picture/QQ20180916091025.jpg");
        //获取文件名，包括后缀
        String fileName = multipartFile.getOriginalFilename();
        //获取后缀，对文件重命名
        String suffix = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        String key = System.currentTimeMillis() + suffix;
        logger.info("key====================:" + key);
        String objectName = directory + key;
        logger.info("objectName==========" + objectName);
        logger.info("***************");
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(multipartFile.getBytes()));
        logger.info("上传成功\n");
        //Date expiration = new Date(new Date().getTime()+1000*3600*24*10);
        //url = ossClient.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, objectName));
        url = "http://" + bucketName + "." + "oss-cn-shanghai.aliyuncs.com" + "/" + objectName;

        //logger.info("Object：" + key + "存入OSS成功。");
        logger.info("Completed######################" + url);
        return url;
    }
}
