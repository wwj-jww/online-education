package com.weijie.educms.controller;


import com.weijie.commonutils.R;
import com.weijie.educms.bean.CrmBanner;
import com.weijie.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {


    @Autowired
    CrmBannerService bannerService;


    /**
     * 查询前两条banner
     */
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }


}

