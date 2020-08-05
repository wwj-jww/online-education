package com.weijie.staservice.controller;


import com.weijie.commonutils.R;
import com.weijie.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-25
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService service;

    /**
     * 生成统计表中某天注册人数的数据
     * @param day
     * @return
     */
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable("day")String day){
        service.registerCount(day);
        return R.ok();
    }

    /**
     * 生成折线统计图
     */
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable("type")String type,@PathVariable("begin")String begin,@PathVariable("end")String end){
        //要求返回的是数组格式，有日期数组和数量数组，放入map中
        Map<String,Object> map = service.showData(type,begin,end);
        return R.ok().data(map);
    }
}

