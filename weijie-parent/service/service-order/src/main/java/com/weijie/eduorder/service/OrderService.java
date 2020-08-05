package com.weijie.eduorder.service;

import com.weijie.eduorder.bean.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-24
 */
public interface OrderService extends IService<Order> {
    //生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
