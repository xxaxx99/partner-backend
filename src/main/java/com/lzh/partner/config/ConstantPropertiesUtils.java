package com.lzh.partner.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class ConstantPropertiesUtils implements InitializingBean {
 
    //读取配置文件的内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.key-id}")
    private String keyId;
    @Value("${aliyun.oss.file.key-secret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucket-name}")
    private String bucketName;
 
    //定义公共静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
 
    @Override
    public void afterPropertiesSet() {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}