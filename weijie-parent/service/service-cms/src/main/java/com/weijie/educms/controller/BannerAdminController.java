package com.weijie.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijie.commonutils.R;
import com.weijie.educms.bean.CrmBanner;
import com.weijie.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 后台控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    CrmBannerService bannerService;

    /**
     * 分页查询banner
     */
    @GetMapping("/pageBanner/{current}/{size}")
    public R pageBanner(@PathVariable("current") Integer current,
                        @PathVariable("size") Integer size){
        Page<CrmBanner> page = new Page<>(current,size);
        IPage<CrmBanner> iPage = bannerService.page(page, null);
        long total = iPage.getTotal();
        List<CrmBanner> records = iPage.getRecords();
        return R.ok().data("records",records).data("total",total);
    }

    /**
     * 基本查询
     */
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    /**
     * 增加banner
     */
    @PostMapping("/save")
    public R save(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return R.ok();
    }

    /**
     * 删除banner
     */
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id){
        bannerService.removeById(id);
        return R.ok();
    }

    /**
     * 修改banner
     */
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }



}

