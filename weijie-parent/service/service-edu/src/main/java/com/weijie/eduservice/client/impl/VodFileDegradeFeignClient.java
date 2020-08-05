package com.weijie.eduservice.client.impl;

import com.weijie.commonutils.R;
import com.weijie.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断器容错处理
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("time out");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
