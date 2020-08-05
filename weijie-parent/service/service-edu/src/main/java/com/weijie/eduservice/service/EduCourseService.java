package com.weijie.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weijie.eduservice.bean.frontvo.CourseFrontVo;
import com.weijie.eduservice.bean.vo.CourseInfoVo;
import com.weijie.eduservice.bean.vo.CoursePublishVo;
import com.weijie.eduservice.bean.frontvo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程信息
    String saveCourseInfo(CourseInfoVo infoVo);
    //根据课程id返回课程信息
    CourseInfoVo getCourseInfo(String courseId);
    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
    //删除课程
    void removeCourse(String courseId);
    //条件查询带分页
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    //获取课程信息，多表查询
    CourseWebVo getBaseCourseInfo(String courseId);
}
