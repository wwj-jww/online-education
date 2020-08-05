package com.weijie.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.commonutils.JwtUtils;
import com.weijie.commonutils.R;
import com.weijie.commonutils.ordervo.CourseWebVoOrder;
import com.weijie.eduservice.bean.EduCourse;
import com.weijie.eduservice.bean.chapter.ChapterVo;
import com.weijie.eduservice.bean.frontvo.CourseFrontVo;
import com.weijie.eduservice.bean.frontvo.CourseWebVo;
import com.weijie.eduservice.client.OrderClient;
import com.weijie.eduservice.service.EduChapterService;
import com.weijie.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    EduCourseService courseService;
    @Autowired
    EduChapterService chapterService;
    @Autowired
    OrderClient orderClient;


    /**
     * 条件查询带分页
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable("page") long page,
                                 @PathVariable("limit") long limit,
                                 @RequestBody CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * 课程详情页面展示
     */
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable("courseId") String courseId, HttpServletRequest request){
        //获取课程信息，多表查询
        CourseWebVo courseWebVo =  courseService.getBaseCourseInfo(courseId);

        //根据课程id获取章节小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询课程支付状态
        String jwtToken = request.getHeader("token");
        if(jwtToken != null){
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Boolean buyCourse = orderClient.isBuyCourse(courseId,memberId);
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
        }else {
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
        }

    }

    /**
     * 根据课程id返回课程信息
     */
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
