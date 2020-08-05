package com.weijie.educenter.controller;


import com.weijie.commonutils.JwtUtils;
import com.weijie.commonutils.R;
import com.weijie.commonutils.ordervo.UcenterMemberOrder;
import com.weijie.educenter.bean.UcenterMember;
import com.weijie.educenter.bean.vo.RegisterVo;
import com.weijie.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-21
 */
@RestController
//@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;

    /**
     * 用户登录
     * @param member
     * @return
     */
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member){
        //登陆成功，返回token通过jwt生成（单点登录）
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }


    /**
     * 用户注册
     */
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 获取token用户信息
     */
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用工具类获取id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //查询出对应id的用户
        UcenterMember member = memberService.getById(id);
        return R.ok().data("userInfo",member);
    }


    /**
     * 根据id获取用户信息
     */
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 查询某一天的注册人数
     */
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
       Integer count = memberService.countRegisterDay(day);
       return R.ok().data("countRegister",count);
    }
}

