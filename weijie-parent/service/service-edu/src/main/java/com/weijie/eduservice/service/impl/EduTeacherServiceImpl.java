package com.weijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.eduservice.bean.EduTeacher;
import com.weijie.eduservice.mapper.EduTeacherMapper;
import com.weijie.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 分页查询的方法
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //调用分页方法，页面对象（page）有数据了
        baseMapper.selectPage(pageParam,wrapper);

        //通过page对象获取分页信息
        baseMapper.selectPage(pageParam,wrapper);

        List<EduTeacher> records = pageParam.getRecords();//获取当前页记录
        long current = pageParam.getCurrent();//获取当前页
        long pages = pageParam.getPages();//获取总页数
        long size = pageParam.getSize();//获取当前页记录数
        long total = pageParam.getTotal();//获取总记录数
        boolean hasNext = pageParam.hasNext();//是否有下一页
        boolean hasPrevious = pageParam.hasPrevious();//是否有上一页

        //放入所有分页对象信息返回
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
}
