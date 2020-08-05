package com.weijie.oss.service.impl;

import com.weijie.oss.service.OssService;
import com.weijie.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    /**
     * 上传头像到oss
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {


        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            //获取实际文件名
            String filename = file.getOriginalFilename();
            //添加UUID区分文件避免重复的覆盖问题
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //34hff42df4hg11.jpg
            filename = uuid + filename;

            //上传的文件做分类 根据日期分类--->年/月/日
            String dataPath = new DateTime().toString("yyyy/MM/dd");

            //做拼接 2020/7/29/34hff42df4hg11.jpg
            filename = dataPath +"/"+ filename;

            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //返回文件的url
            //https://edu-0715.oss-cn-beijing.aliyuncs.com/11.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;

            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
