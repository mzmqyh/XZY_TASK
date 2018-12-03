package com.jnshu.myutils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class SendCloudMailUtil {
    Logger logger = Logger.getLogger(SendCloudMailUtil.class);
    private String url = "http://api.sendcloud.net/apiv2/mail/send";
    private String apiUser = "dreamhonymail_test_d54mL3";
    private String apiKey = "36fol6NfE04dpAby";
    private String fromName = "dreamhonymail";
    private String from = "service@sendcloud.im";
    private String subject = "来自SendCloud的邮件";
    private String html = "你太棒了！你已成功的从SendCloud发送了一个验证码：";

    public SendCloudMailUtil() {
    }

    public SendCloudMailUtil(String url, String apiUser, String apiKey, String fromName, String from, String subject, String html) {
        this.url = url;
        this.apiUser = apiUser;
        this.apiKey = apiKey;
        this.fromName = fromName;
        this.from = from;
        this.subject = subject;
        this.html = html;
    }

    public String sendCloudSentEmail(String email) throws IOException {
        logger.info("sendCloudSentEmail=====" + email);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List params = new ArrayList();
        //验证码长度
        final int verifyCodeLength = 6;
        //验证码类型0：数字；
        final int VerifyCodeType = 0;
        //设置超时时间-可自行调整
        String emailCode = RandomUtil.getCode(verifyCodeLength, VerifyCodeType);
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("fromName", fromName));
        params.add(new BasicNameValuePair("to", email));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", html + emailCode));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        //请求
        HttpResponse response = httpclient.execute(httpPost);
        //处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            return emailCode;
        } else {
            System.err.println("error");
        }
        httpPost.releaseConnection();
        return null;
    }
}
