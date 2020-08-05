package com.weijie.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.EduCourse;
import com.weijie.eduservice.bean.EduTeacher;
import com.weijie.eduservice.service.EduCourseService;
import com.weijie.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {


    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询的方法
     */
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") long page,@PathVariable("limit") long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }


    /**
     * 讲师详情信息
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId") String teacherId){
        //获取讲师信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        //获取课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
