package com.weijie.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.eduservice.bean.EduSubject;
import com.weijie.eduservice.bean.excel.SubjectData;
import com.weijie.eduservice.service.EduSubjectService;
import com.weijie.servicebase.exceptionhandler.WeiJieException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //SubjectExcelListener类无法交给spring管理，因为他需要手动new对象，所以用手动注入的方式(写有参构造传入EduSubjectService)

    private EduSubjectService service;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService service) {
        this.service = service;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new WeiJieException(20001,"文件数据为空");
        }

    //一行一行添加，每次添加两个值，一个一级分类，一个二级分类
    //判断一级分类是否重复添加
        EduSubject oneSubject = this.existOneSubject(subjectData.getOneSubjectName(), service);
        if(oneSubject == null){//数据库没有相同数据，可以添加
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            service.save(oneSubject); //当保存到数据库后，mp就会自动生成一级分类pid值
        }
    //判断二级分类是否重复
    //获取保存到数据库的一级分类的pid值
        String pid = oneSubject.getId();

        EduSubject twoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), service, pid);
        if(twoSubject == null){//数据库没有相同数据，可以添加
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            service.save(twoSubject); //当保存到数据库后，mp就会自动生成一级分类pid值
        }

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(String name,EduSubjectService service){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = service.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name,EduSubjectService service,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = service.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
