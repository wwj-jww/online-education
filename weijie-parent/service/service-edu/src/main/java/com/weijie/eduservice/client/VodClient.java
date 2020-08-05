package com.weijie.eduservice.client;

import com.weijie.commonutils.R;
import com.weijie.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;
import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class) //指明要调用的服务
public interface VodClient {

    /**
     * 根据id删除阿里云视频
     */
    //指明要调用服务的方法
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    /**
     * 根据id集合删除阿里云多个视频
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@PathParam("videoIdList") List<String> videoIdList);
}
