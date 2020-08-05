package com.weijie.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import com.weijie.vod.Utils.ConstantVodUtils;
import com.weijie.vod.Utils.InitVodClient;
import com.weijie.vod.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            //6 - What If I Want to Move Faster.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称： 6 - What If I Want to Move Faster
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id删除多个视频的方法
     * @param videoIdList id数组
     */
    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建request
            DeleteVideoRequest request = new DeleteVideoRequest();
            //生成连续视频id
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            //设置连续视频id
            request.setVideoIds(videoIds);
            //调用对象方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new WeiJieException(20001,"删除失败");
        }
    }
}
