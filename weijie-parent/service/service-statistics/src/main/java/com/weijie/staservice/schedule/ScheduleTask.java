package com.weijie.staservice.schedule;

import com.weijie.staservice.service.StatisticsDailyService;
import com.weijie.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    StatisticsDailyService service;

    /**
     * 定时任务：每天凌晨一点统计昨天的系统信息
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        service.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }

}
