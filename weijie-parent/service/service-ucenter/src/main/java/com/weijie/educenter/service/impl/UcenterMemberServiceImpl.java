package com.weijie.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.commonutils.JwtUtils;
import com.weijie.commonutils.MD5;
import com.weijie.educenter.bean.UcenterMember;
import com.weijie.educenter.bean.vo.RegisterVo;
import com.weijie.educenter.mapper.UcenterMemberMapper;
import com.weijie.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 用户登录
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new WeiJieException(20001,"登录失败");
        }

        //手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileMember == null) {//没有这个手机号
            throw new WeiJieException(20001,"登录失败");
        }

        /**
         * 数据库中的密码是加密的，所以要将用户输入的密码加密处理后和数据库存在的密码比较
         */
        //密码是否正确
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new WeiJieException(20001,"登录失败");
        }

        //用户是否禁用
        if(mobileMember.getIsDisabled()){
            throw new WeiJieException(20001,"登录失败");
        }
        //登录成功
        //生成token
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return token;
    }

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 用户注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(password) ||StringUtils.isEmpty(code)){
            throw new WeiJieException(20001,"注册失败");
        }


        //判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new WeiJieException(20001,"注册失败");
        }

        //判断手机号是不是重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);

        if(count > 0) {
            throw new WeiJieException(20001,"注册失败");
        }

        //验证通过，添加进数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }


    /**
     * 判断数据表里面是否存在相同微信信息，根据openid判断
     * @param openid
     * @return
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }


    /**
     * 查询某一天的注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
