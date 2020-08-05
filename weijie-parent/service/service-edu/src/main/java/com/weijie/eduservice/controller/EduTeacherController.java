package com.weijie.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.commonutils.R;
import com.weijie.eduservice.bean.EduTeacher;
import com.weijie.eduservice.bean.vo.TeacherQuery;
import com.weijie.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
@Api(description="讲师管理")
public class EduTeacherController {


    @Autowired
    EduTeacherService service;



    /**访问路径：http://localhost:8081/eduservice/teacher/findAll
     * 查询所有讲师的数据集合
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeachers(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 逻辑删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable("id") String id){

        boolean b = service.removeById(id);

        if(b){
           return R.ok();
        }else {
           return R.error();
        }
    }
    /**
     * 分页查询
     */
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageTeacherList(@PathVariable("current") Integer current,
                             @PathVariable("size") Integer size){
        Page<EduTeacher> page = new Page<>(current,size);
        IPage<EduTeacher> iPage = service.page(page, null);
        long total = iPage.getTotal();
        List<EduTeacher> records = iPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 多组合条件查询带分页
     * @RequestBody 将客户端传过来的json数据封装到对应的bean对象中
     * 请求方式改为post，因为客户端传值方式为json
     * required = false 不是必须条件，因为条件可以为空
     */
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable("current") Integer current,
                                  @PathVariable("size") Integer size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //分页
        Page<EduTeacher> page = new Page<>(current, size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多组合条件查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }

        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //根据创建时间降序排序
        wrapper.orderByDesc("gmt_create");

        IPage<EduTeacher> iPage = service.page(page, wrapper);

        long total = iPage.getTotal();
        List<EduTeacher> records = iPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 添加讲师
     */
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = service.save(eduTeacher);
        if(b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据id回显讲师
     */
    @GetMapping("/getTeacherById/{id}")
    public R getTeacherById(@PathVariable("id") String id){
        EduTeacher teacher = service.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 修改讲师 updateById因为传过来的对象里包含了id 所以只需要根据id改对象即可
     */
    @PostMapping("/updateTeacher")
    public R updateTeacherById(@RequestBody EduTeacher teacher){
        boolean b = service.updateById(teacher);
        if(b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

