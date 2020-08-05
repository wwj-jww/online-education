package com.weijie.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.eduservice.bean.EduSubject;
import com.weijie.eduservice.bean.excel.SubjectData;
import com.weijie.eduservice.bean.subject.OneSubject;
import com.weijie.eduservice.bean.subject.TwoSubject;
import com.weijie.eduservice.listener.SubjectExcelListener;
import com.weijie.eduservice.mapper.EduSubjectMapper;
import com.weijie.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类，把上传的文件内容读出来
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService service) {
        try {
            InputStream stream = file.getInputStream();
            EasyExcel.read(stream, SubjectData.class,new SubjectExcelListener(service)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  课程列表展示 树形结构
     * @return
     */
    @Override
    public List<OneSubject> getAllOneAndTwoSubject() {

        //查询一级分类 (条件查询) mp自动注入了baseMapper
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> oneList = baseMapper.selectList(wrapper1);

        //查询二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> twoList = baseMapper.selectList(wrapper2);

        //最终返回的集合
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        for (int i = 0; i < oneList.size(); i++) {
            //取出单个数据
            EduSubject subject1 = oneList.get(i);
            //要封装的数据格式
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject1.getId());
//            oneSubject.setTitle(subject1.getTitle());

            //参数：get的数据   set的数据
            BeanUtils.copyProperties(subject1,oneSubject);

            finalSubjectList.add(oneSubject);

            //此一级分类所有二级分类的集合
            List<TwoSubject> twoSubject = new ArrayList<>();

            for (int m = 0; m < twoList.size(); m++) {

                EduSubject subject2 = twoList.get(m);
                if(subject2.getParentId().equals(subject1.getId())){//判断是不是这个一级分类的二级分类
                    //封装二级分类
                    TwoSubject sj2 = new TwoSubject();
                    BeanUtils.copyProperties(subject2,sj2);
                    //封装好的数据加进集合
                    twoSubject.add(sj2);
                }


            }
            //设置一级分类的二级分类集合
            oneSubject.setChildren(twoSubject);


        }



        return finalSubjectList;
    }
}
