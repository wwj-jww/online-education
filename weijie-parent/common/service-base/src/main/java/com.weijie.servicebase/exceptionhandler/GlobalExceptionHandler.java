package com.weijie.servicebase.exceptionhandler;

import com.weijie.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@Slf4j
@ControllerAdvice //控制器通知 所有的控制器都可以使用这个类
    public class GlobalExceptionHandler {

        //指定当发生什么异常时这个方法才执行
        @ExceptionHandler(Exception.class)//所有异常都执行
        @ResponseBody //返回json数据
        public R error(Exception e){
            log.error(ExceptionUtil.getMessage(e));
            e.printStackTrace();
            return R.error().message("执行了全局异常处理");
        }


    //自定义异常处理
    //指定当发生什么异常时这个方法才执行
    @ExceptionHandler(WeiJieException.class)//所有异常都执行
    @ResponseBody //返回json数据
    public R myError(WeiJieException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }




}
