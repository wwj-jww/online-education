package com.weijie.eduservice.controller;


import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.EduVideo;
import com.weijie.eduservice.client.VodClient;
import com.weijie.eduservice.service.EduVideoService;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @Autowired
    VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable("id") String id){
        videoService.removeById(id);
        return R.ok();
    }


    /**
     * 根据id回显小节
     */
    @GetMapping("/getVideoInfo/{videoId}")
    public R updateChapterById(@PathVariable("videoId")String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }


    /**
     * 修改小节，和对应阿里云的视频
     */
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        //因为删除视频需要视频id，而小节对象包含视频id
        EduVideo video = videoService.getById(eduVideo);
        String videoSourceId = video.getVideoSourceId();

        if(!StringUtils.isEmpty(videoSourceId)){
            //远程调用，删除视频
            R r = vodClient.removeAlyVideo(videoSourceId);
            if(r.getCode()==20001){
                throw new WeiJieException(20001,"删除视频失败...熔断器");
            }
        }
        videoService.updateById(eduVideo);
        return R.ok();
    }




}

