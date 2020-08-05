package com.weijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.eduservice.bean.EduCourse;
import com.weijie.eduservice.bean.EduCourseDescription;
import com.weijie.eduservice.bean.frontvo.CourseFrontVo;
import com.weijie.eduservice.bean.vo.CourseInfoVo;
import com.weijie.eduservice.bean.vo.CoursePublishVo;
import com.weijie.eduservice.bean.frontvo.CourseWebVo;
import com.weijie.eduservice.mapper.EduCourseMapper;
import com.weijie.eduservice.service.EduChapterService;
import com.weijie.eduservice.service.EduCourseDescriptionService;
import com.weijie.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weijie.eduservice.service.EduVideoService;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入小节
    @Autowired
    private EduVideoService videoService;

    //注入章节
    @Autowired
    private EduChapterService chapterService;

    //因为都被spring管理，所以直接注入课程信息描述表
    @Autowired
    private EduCourseDescriptionService descriptionService;


    /**
     *  添加课程信息
     */
    @Override
    public String saveCourseInfo(CourseInfoVo infoVo) {
        //添加课程信息

        //将CourseInfoVo 转换为EduCourse
        EduCourse eduCourse = new EduCourse();
        //封装属性
        BeanUtils.copyProperties(infoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){//添加失败
            throw new WeiJieException(20001,"添加课程信息失败");
        }
        //当课程信息添加成功后，获取数据库生成的课程id，给描述表中设置id关联
        String cid = eduCourse.getId();

        //添加课程描述信息
        //向课程描述表中添加
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(infoVo.getDescription());
        //给描述表中设置id关联
        courseDescription.setId(cid);
        descriptionService.save(courseDescription);

        //返回课程id
        return cid;
    }

    /**
     * 根据课程id返回课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //查询课程表信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);


        //查询课程描述表信息
        EduCourseDescription eduCourseDescription = descriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i == 0){
            throw new WeiJieException(20001,"课程信息修改失败");
        }

        //修改课程描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //保持两表id一致
        courseDescription.setId(courseInfoVo.getId());
        descriptionService.updateById(courseDescription);

    }

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    /**
     * 删除课程
     * @param courseId
     */
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //根据课程id删除描述
        descriptionService.removeById(courseId);

        //根据课程id删除课程本身
        int i = baseMapper.deleteById(courseId);

        if (i == 0){
            throw new WeiJieException(20001,"删除失败");
        }
    }


    /**
     * 条件查询带分页
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断有没有一级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        //判断有没有二级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        //查完后page对象有数据了，获取数据放入map集合返回
        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }


    /**
     * 获取课程信息，多表查询
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }


}



