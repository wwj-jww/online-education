package com.weijie.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 因为是冗余代码，所以设置工具类，用value注解从配置文件中获取值
 * 但因为是private的 所以外面用不了，所以实现一个接口 [InitializingBean]
 * 里面有一个方法[afterPropertiesSet]，在值注入之后执行，
 * 把属性赋值给定义的public static的变量，对外能访问
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //value注解来读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //定义公开静态常量
    public static String END_POIND;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POIND = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
