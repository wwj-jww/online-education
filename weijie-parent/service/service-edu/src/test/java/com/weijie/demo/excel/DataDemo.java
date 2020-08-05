package com.weijie.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


@Data
public class DataDemo {//（和excel对应的实体类）

    @ExcelProperty(value = "学生编号",index = 0) //生成excel对应的属性名（表头）
    private Integer no;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}
