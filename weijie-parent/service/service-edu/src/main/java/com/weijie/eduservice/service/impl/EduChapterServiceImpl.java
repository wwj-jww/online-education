package com.weijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.eduservice.bean.EduChapter;
import com.weijie.eduservice.bean.EduVideo;
import com.weijie.eduservice.bean.chapter.ChapterVo;
import com.weijie.eduservice.bean.chapter.VideoVo;
import com.weijie.eduservice.mapper.EduChapterMapper;
import com.weijie.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weijie.eduservice.service.EduVideoService;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    /**
     * 根据课程id查询章节小节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterWrapper);


        //根据课程id查询小节 自动装配
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(videoWrapper);

        List<ChapterVo> chapterVoList = new ArrayList<>();

        //遍历封装章节数据
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter =  eduChapterList.get(i);

            ChapterVo chapterVo = new ChapterVo();

            BeanUtils.copyProperties(eduChapter,chapterVo);

            chapterVoList.add(chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();

            //遍历封装小姐数据(嵌套循环) 一个章节多个小结
            for (int m = 0; m < eduVideoList.size(); m++) {

                EduVideo eduVideo = eduVideoList.get(m);
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();

                    BeanUtils.copyProperties(eduVideo,videoVo);

                    videoVoList.add(videoVo);
                }

            }

            chapterVo.setChildren(videoVoList);
        }

        return chapterVoList;
    }

    /**
     * 删除章节
     * @param chapterId
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);

        if(count > 0){//有小节
            throw new WeiJieException(20001,"不能删除");
        }else {//删除
            //影响行数大于0删除成功
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
