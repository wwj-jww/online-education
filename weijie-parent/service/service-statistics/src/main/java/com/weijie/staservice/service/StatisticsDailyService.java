package com.weijie.staservice.service;

import com.weijie.staservice.bean.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-25
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    //生成统计表中某天注册人数的数据
    void registerCount(String day);
    //生成折线统计图
    Map<String, Object> showData(String type, String begin, String end);
}
