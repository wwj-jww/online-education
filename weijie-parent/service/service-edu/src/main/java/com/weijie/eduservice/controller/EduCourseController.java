package com.weijie.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.EduCourse;
import com.weijie.eduservice.bean.vo.CourseInfoVo;
import com.weijie.eduservice.bean.vo.CoursePublishVo;
import com.weijie.eduservice.bean.vo.CourseQuery;
import com.weijie.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    EduCourseService service;

    /**
     * 添加课程信息
     * @param infoVo
     * @return
     */
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo infoVo){
        //返回课程的id，为之后添加章节小节做准备
        String id = service.saveCourseInfo(infoVo);
        return R.ok().data("courseId",id);
    }

    /**
     * 根据课程id返回课程信息
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        //返回课程的id，为之后添加章节小节做准备
        CourseInfoVo courseInfoVo = service.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     * 修改课程信息
     */
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        service.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     */
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable("id") String id) {
        CoursePublishVo coursePublishVo = service.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    /**
     * 发布课程
     */
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId")String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);//根据id修改
        course.setStatus("Normal");
        service.updateById(course);//局部修改 改状态，所以传id和状态
        return R.ok();
    }

    /**
     * 查询所有课程列表
     */
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = service.list(null);
        return R.ok().data("list",list);
    }


    /**
     * 删除课程
     */
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable("courseId")String courseId){
        service.removeCourse(courseId);
        return R.ok();
    }

    /**
     * 分页查询
     */
    @GetMapping("/pageCourse/{current}/{size}")
    public R pageCourseList(@PathVariable("current") Integer current,
                             @PathVariable("size") Integer size){
        Page<EduCourse> page = new Page<>(current,size);
        IPage<EduCourse> iPage = service.page(page, null);
        long total = iPage.getTotal();//总页数
        List<EduCourse> records = iPage.getRecords();//总记录数
        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 多组合条件查询带分页
     * @RequestBody 将客户端传过来的json数据封装到对应的bean对象中
     * 请求方式改为post，因为客户端传值方式为json
     * required = false 不是必须条件，因为条件可以为空
     */
    @PostMapping("/pageCourseCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable("current") Integer current,
                                  @PathVariable("size") Integer size,
                                  @RequestBody(required = false) CourseQuery courseQuery){
        //分页
        Page<EduCourse> page = new Page<>(current, size);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //多组合条件查询
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();


        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(status) ) {
            queryWrapper.eq("status", status);
        }

        //根据创建时间降序排序
        queryWrapper.orderByDesc("gmt_create");

        IPage<EduCourse> iPage = service.page(page, queryWrapper);

        long total = iPage.getTotal();
        List<EduCourse> records = iPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }
}

