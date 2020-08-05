package com.weijie.educms.service;

import com.weijie.educms.bean.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
public interface CrmBannerService extends IService<CrmBanner> {
    //查询前两条banner
    List<CrmBanner> selectAllBanner();
}
