package com.weijie.eduorder.service;

import com.weijie.eduorder.bean.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-24
 */
public interface PayLogService extends IService<PayLog> {
    //根据订单号生成支付二维码
    Map createNatvie(String orderNo);
    //根据订单号查询二维码支付状态
    Map<String, String> queryPayStatus(String orderNo);
    //向支付表添加记录，更新订单表状态
    void updateOrdersStatus(Map<String, String> map);
}
