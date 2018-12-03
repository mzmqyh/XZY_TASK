package com.jnshu.myutils;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @ClassName TencentOOSUtil
 * @Auther: QYH
 * @Date: 2018/11/29 22:36
 * @Description: TODO
 * @Version 1.0
 */
@Component
public class TencentOOSUtil {
    Logger logger = Logger.getLogger(TencentOOSUtil.class);
    public String region = "ap-shanghai";//区域北京则  beijing
    //appId开发者访问 COS 服务时拥有的用户维度唯一资源标识，用以标识资源
    public String appId = "1258186039";
    public String bucketName = "tencent-cos"; //桶的名称
    public String cosbucketname = bucketName + "-" + appId;
    ExecutorService threadPool = Executors.newFixedThreadPool(32);
    // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
    public TransferManager transferManager;

    public COSClient getCOSClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        //SecretId 是用于标识 API 调用者的身份
        String SecretId = "AKID56IQWWE6kgTLF2ckv07AFQTYGnJpMmIt";
        //SecretKey是用于加密签名字符串和服务器端验证签名字符串的密钥
        String SecretKey = "hJxcFngWNMNIAUjsqpjRzX6ayLjYL8sz";
        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        // 2 设置bucket的区域,
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端，cre为 指定要上传到 COS 上的路径
        COSClient cosClient = new COSClient(cred, clientConfig);
        //ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        //transferManager = new TransferManager(cosClient, threadPool);
        return cosClient;
    }

    /**
     * 上传
     * //
     */
    public URL upload(COSClient cosClient, String objectName, File localFile, String cosbucketname) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
        String state;
        PutObjectResult putObjectResult;
        URL url = null;
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        // File localFile = new File("src/test/resources/len5M.txt");
        // 指定要上传到 COS 上对象键,路径名+文件名（包括后缀）
        // 对象键（objectName）是对象在存储桶中的唯一标识。
        // 例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，
        // 对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosbucketname, objectName, localFile);
            putObjectResult = cosClient.putObject(putObjectRequest);
            if (putObjectResult != null) {
                // Date expiration = new Date(new Date().getTime() + 10 * 1000);
                url = cosClient.generatePresignedUrl(new GeneratePresignedUrlRequest(cosbucketname, objectName, HttpMethodName.GET));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cosClient != null) {
                // cosClient.shutdown();
            }
        }
   /*     final URL[] url = {null};
        new Thread(new Runnable() {
            TransferManager transferManager = null;
            public void run() {
                try {
                    System.out.println("上传开始时间:" + simpleDateFormat.format(new Date()));
                    // .....(提交上传下载请求, 如下文所属)
                    // bucket 的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
                    String bucket = bucketName + "-" + appId;
                    //本地文件路径
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, objectName, localFile);
                    // 本地文件上传
                    Upload upload = transferManager.upload(putObjectRequest);
                    // 异步（如果想等待传输结束，则调用 waitForUploadResult）
                    //UploadResult uploadResult = upload.waitForUploadResult();
                    //同步的等待上传结束waitForCompletion
                    upload.waitForCompletion();
                    System.out.println("上传结束时间:" + simpleDateFormat.format(new Date()));
                    System.out.println("上传成功");
                    //获取上传成功之后文件的下载地址
                     url[0] = cosClient.generatePresignedUrl(bucketName + "-" + appId, objectName,
                            new Date(new Date().getTime() + 5 * 60 * 10000));
                    System.out.println(url[0]);
                } catch (Throwable tb) {
                    System.out.println("上传失败");
                    tb.printStackTrace();
                } finally {
                    // 关闭 TransferManger
                    transferManager.shutdownNow();
                }
            }
        }).start();
         ;*/

        return url;
    }

    /**
     * 下载
     */
    public boolean download(COSClient cosClient, String toLocalPath, String objectName, String cosbucketname) {
        logger.info("download==========" + toLocalPath);
        try {
            //下载到本地指定路径
            File localDownFile = new File(toLocalPath);
            System.out.println(localDownFile.getPath());
            GetObjectRequest getObjectRequest = new GetObjectRequest(cosbucketname, objectName);
            // 下载文件
            cosClient.getObject(getObjectRequest, localDownFile);
            System.out.println("下载成功");
            return true;
        } catch (Throwable tb) {
            System.out.println("下载失败");
            tb.printStackTrace();
            return false;
        } finally {
            // 关闭 TransferManger
            // cosClient.shutdown();
        }
    }

    /**
     * 删除
     */
    public void delete(COSClient cosClient, String bucketName, String key) {
        boolean flag = false;
        new Thread(new Runnable() {
            public void run() {
                // 指定要删除的 bucket 和路径
                try {
                    cosClient.deleteObject(bucketName + "-" + appId, key);
                    System.out.println("删除成功");
                } catch (Throwable tb) {
                    System.out.println("删除文件失败");
                    tb.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 列举
     *
     * @return
     */
    public List <COSObjectSummary> listObject(COSClient cosClient, String cosbucketname) {
        //获取bucket下成员(设置delimiter)
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(cosbucketname);
        //设置list的prefix,表示list出来的key都是以这个prefix开始img/
        listObjectsRequest.setPrefix("test/");
        // 设置 delimiter 为/, 即获取的是直接成员，不包含目录下的递归子成员
        listObjectsRequest.setDelimiter("/");
        // 设置marker,(marker由上一次list获取到，或者第一次 list marker 为空)
        listObjectsRequest.setMarker("");
        // 设置最多 list 100个成员,（如果不设置, 默认为1000个，最大允许一次 list 1000个 key）
        //    listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = cosClient.listObjects(cosbucketname);

        // 获取下次 list 的 marker
        String nextMarker = objectListing.getNextMarker();
        // 判断是否已经 list 完, 如果 list 结束, 则 isTruncated 为 false, 否则为 true
        boolean isTruncated = objectListing.isTruncated();
        List <COSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        cosClient.shutdown();
        return objectSummaries;
    }

   /* public  void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(TencentOOSUtil.class);
        COSClient cosClient = getCOSClient();
        OSSClient oosClient = AliyunOOSUtil.getOSSClient();
        //上传
        //File file = new File("D:/picture/QQ图片20181019154506.jpg");
        //String fileName = file.getName();
        //获取后缀，对文件重命名
        //String suffix ="."+ fileName.substring(fileName.lastIndexOf(".") + 1);
        // String key = System.currentTimeMillis()+suffix;
        //logger.info("key====================:"+key);
        // String objectName = directory +"/"+ "1543558580530"+".png";
        //logger.info("objectName=========="+objectName);
        // URL url = upload(cosClient,objectName,file,cosbucketname);
        // logger.info("url================"+url);
        //下载
        String toLocalPath = "D://myloadfile/";
        logger.info("cosbucketname============:" + cosbucketname);
        List <COSObjectSummary> objects = listObject(cosClient, cosbucketname);
        for (COSObjectSummary object : objects) {
            logger.info("******************" + object.getStorageClass() + "\n");
            String key = object.getKey();
            String localPath = toLocalPath + key;
            logger.info("下载结果" + download(cosClient, localPath, key, cosbucketname));
            String oosPath = "task7img/" + key;
            File file = new File(localPath);
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            logger.info("oosPath" + key);
            logger.info("迁移后的url" + AliyunOOSUtil.uploadByFile(oosClient, multipartFile, AliyunOOSUtil.bucketName, oosPath));
        }
    }*/
}
