package com.weijie.eduservice.controller;


import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.subject.OneSubject;
import com.weijie.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService service;

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        service.saveSubject(file,service);
        return R.ok();
    }

    /**
     * 课程列表展示 树形结构
     */
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        //得到所有的分类数据
        //因为一级分类里包含了二级分类，所以泛型为一级分类
        List<OneSubject> list = service.getAllOneAndTwoSubject();
        return R.ok().data("list",list);
    }
}

