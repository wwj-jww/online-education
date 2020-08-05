package com.weijie.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.commonutils.JwtUtils;
import com.weijie.commonutils.R;
import com.weijie.eduorder.bean.Order;
import com.weijie.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-24
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 生成订单的方法
     * @return
     */
    //1 生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据订单id回显订单信息
     */
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId") String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    /**
     * 根据课程id和用户id判断课程支付状态
     */
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId")String courseId,@PathVariable("memberId")String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count>0){//支付成功
            return true;
        }else {
            return false;
        }
    }

}

