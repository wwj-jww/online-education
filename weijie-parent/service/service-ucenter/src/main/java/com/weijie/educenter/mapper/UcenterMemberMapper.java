package com.weijie.educenter.mapper;

import com.weijie.educenter.bean.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-21
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //查询某一天的注册人数
    Integer countRegisterDay(@Param("day") String day);
}
