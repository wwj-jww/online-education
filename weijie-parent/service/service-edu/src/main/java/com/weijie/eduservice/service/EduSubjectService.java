package com.weijie.eduservice.service;

import com.weijie.eduservice.bean.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weijie.eduservice.bean.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
public interface EduSubjectService extends IService<EduSubject> {
    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService service);

    //课程列表展示 树形结构
    List<OneSubject> getAllOneAndTwoSubject();
}
