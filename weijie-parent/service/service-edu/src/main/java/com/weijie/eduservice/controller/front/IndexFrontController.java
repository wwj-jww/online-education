package com.weijie.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.EduCourse;
import com.weijie.eduservice.bean.EduTeacher;
import com.weijie.eduservice.service.EduCourseService;
import com.weijie.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    /**
     * 查询前四条讲师记录，前八条课程记录
     */
    @GetMapping("/index")
    public R index(){
        //查询最新八个课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapperCourse);

        //查询前四名讲师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByAsc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
