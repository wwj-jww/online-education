package com.weijie.msmservice.controller;

import com.weijie.commonutils.R;
import com.weijie.msmservice.service.MsmService;
import com.weijie.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 发送短信的方法
     */
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone){
        //能获取到说明没过期
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //生成随机数
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        //调用发送的方法
        boolean flag= msmService.send(map,phone);

        if(flag){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);//放进redis
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }

}
