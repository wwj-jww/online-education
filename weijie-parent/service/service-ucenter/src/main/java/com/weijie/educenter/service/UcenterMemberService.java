package com.weijie.educenter.service;

import com.weijie.educenter.bean.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weijie.educenter.bean.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-21
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //用户登录
    String login(UcenterMember member);
    //用户注册
    void register(RegisterVo registerVo);
    //判断数据表里面是否存在相同微信信息，根据openid判断
    UcenterMember getOpenIdMember(String openid);
    //查询某一天的注册人数
    Integer countRegisterDay(String day);
}
