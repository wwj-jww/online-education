package com.weijie.eduservice.service;

import com.weijie.eduservice.bean.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weijie.eduservice.bean.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
public interface EduChapterService extends IService<EduChapter> {
    //根据课程id查询章节小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    //删除章节
    boolean deleteChapter(String chapterId);
    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
