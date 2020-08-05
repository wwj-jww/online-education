package com.weijie.eduservice.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


    @ApiModel(value = "Course查询对象", description = "课程查询对象封装")
    @Data
    public class CourseQuery{


        @ApiModelProperty(value = "课程名称")
        private String title;

        @ApiModelProperty(value = "发布状态")
        private String status;


}
