package com.weijie.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //全参构造器
@NoArgsConstructor //无参构造器
@Data
public class WeiJieException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

    @Override
    public String toString() {
        return "WeiJieException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }

}
