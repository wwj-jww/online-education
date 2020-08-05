package com.weijie.oss.controller;

import com.weijie.oss.service.OssService;
import com.weijie.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) { //获取上传文件  MultipartFile

        //返回上传到oss的文件路径 用于访问和下载
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
