package com.weijie.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijie.educms.bean.CrmBanner;
import com.weijie.educms.mapper.CrmBannerMapper;
import com.weijie.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    /**
     * 查询前两条banner
     * @return
     */
   // @Cacheable(value = "banner", key = "'selectIndexList'" )
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法用于在后追加语句
        wrapper.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
