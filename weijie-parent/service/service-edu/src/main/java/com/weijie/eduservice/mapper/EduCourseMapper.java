package com.weijie.eduservice.mapper;

import com.weijie.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijie.eduservice.bean.vo.CoursePublishVo;
import com.weijie.eduservice.bean.frontvo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询发布的课程信息
    public CoursePublishVo  getPublishCourseInfo(String courseId);
    //获取课程信息，多表查询
    CourseWebVo getBaseCourseInfo(String courseId);
}
