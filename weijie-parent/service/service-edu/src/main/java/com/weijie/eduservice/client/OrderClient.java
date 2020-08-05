package com.weijie.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-order")
@Component
public interface OrderClient {
    /**
     * 根据课程id和用户id判断课程支付状态
     */
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId")String courseId, @PathVariable("memberId")String memberId);

}
