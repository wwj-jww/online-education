package com.weijie.eduorder.service.impl;

import com.weijie.commonutils.ordervo.CourseWebVoOrder;
import com.weijie.commonutils.ordervo.UcenterMemberOrder;
import com.weijie.eduorder.bean.Order;
import com.weijie.eduorder.client.EduClient;
import com.weijie.eduorder.client.UcenterClient;
import com.weijie.eduorder.mapper.OrderMapper;
import com.weijie.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weijie.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    UcenterClient ucenterClient;
    @Autowired
    EduClient eduClient;
    /**
     * 生成订单的方法
     * @param courseId
     * @return
     */
    @Override
    public String createOrders(String courseId, String memberId) {
       //远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        Order order = new Order();
        //设置订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);

        //返回订单号
        return order.getOrderNo();
    }


}
