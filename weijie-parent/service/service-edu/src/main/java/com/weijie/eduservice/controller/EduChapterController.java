package com.weijie.eduservice.controller;


import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.EduChapter;
import com.weijie.eduservice.bean.chapter.ChapterVo;
import com.weijie.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService service;

    /**
     * 根据课程id查询章节小节
     * @param
     * @return
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list = service.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     * 添加章节
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        service.save(chapter);
        return R.ok();
    }

    /**
     * 根据id查询章节
     */
    @GetMapping("/getChapterInfo/{chapterId}")
    public R updateChapterById(@PathVariable("chapterId")String chapterId){
        EduChapter eduChapter = service.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        service.updateById(chapter);
        return R.ok();
    }

    /**
     * 删除章节
     */
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId")String chapterId){
        boolean flag = service.deleteChapter(chapterId);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

