package com.weijie.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.weijie.commonutils.R;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import com.weijie.vod.Utils.ConstantVodUtils;
import com.weijie.vod.Utils.InitVodClient;
import com.weijie.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.websocket.server.PathParam;
import java.util.List;

@RequestMapping("/eduvod/video")
@RestController
//@CrossOrigin
public class VodController {

    @Autowired
    VodService service;

    /**
     *上传视频到阿里云
     */
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){
        //返回上传视频id
        String videoId = service.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 根据id删除阿里云视频
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建request
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置视频id
            request.setVideoIds(id);
            //调用对象方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new WeiJieException(20001,"删除失败");
        }

    }


    /**
     * 根据id删除多个视频的方法
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@PathParam("videoIdList") List<String> videoIdList){
        service.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据id获取视频的播放凭证
     */
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new WeiJieException(20001,"获取凭证失败");
        }
    }

}
