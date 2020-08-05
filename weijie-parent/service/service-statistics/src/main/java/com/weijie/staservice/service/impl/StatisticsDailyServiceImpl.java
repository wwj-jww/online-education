package com.weijie.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.commonutils.R;
import com.weijie.staservice.bean.StatisticsDaily;
import com.weijie.staservice.client.UcentClient;
import com.weijie.staservice.mapper.StatisticsDailyMapper;
import com.weijie.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-25
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    UcentClient ucentClient;

    /**
     * 生成统计表中某天注册人数的数据
     * @param day
     */
    @Override
    public void registerCount(String day) {
        //保证数据唯一性，如果数据库有，先删除再添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //获取统计数据
        R r = ucentClient.countRegister(day);
        Integer count = (Integer) r.getData().get("countRegister");

        //加入数据库
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(count);
        daily.setDateCalculated(day);

        daily.setLoginNum(RandomUtils.nextInt(100,200));
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(daily);
    }


    /**
     * 生成折线统计图
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        //查询日期范围
        wrapper.between("date_calculated",begin,end);
        //查询指定的日期对应的指定类型人数
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        //前端需要的是json数组，所以用list封装
        //一个是日期集合，一个是数量集合
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历总数组封装数据
        for (int i = 0; i < dailyList.size(); i++) {
            StatisticsDaily daily = dailyList.get(i);
            //封装日期
            date_calculatedList.add(daily.getDateCalculated());
            //封装数量
            switch (type){
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
            }
        }

        //放入map返回
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);

        return map;
    }
}
