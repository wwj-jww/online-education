package com.weijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.eduservice.bean.EduVideo;
import com.weijie.eduservice.client.VodClient;
import com.weijie.eduservice.mapper.EduVideoMapper;
import com.weijie.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    //根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.select("video_source_id");//根据课程id查到小节，在查指定的列
        List<EduVideo> videoIds = baseMapper.selectList(queryWrapper);

        //方法需要List<String>
        //所以要转换
        List<String> list = new ArrayList<>();
        for (int i = 0; i < videoIds.size(); i++) {
            EduVideo eduVideo = videoIds.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                list.add(videoSourceId);
            }
        }
        if(list.size()>0){
            vodClient.deleteBatch(list);
        }

        //deleteById是根据小节的id删 而要求用课程id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
