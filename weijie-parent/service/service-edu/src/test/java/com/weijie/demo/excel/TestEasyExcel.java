package com.weijie.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
   /*     //文件路径
        String filePath = "E:\\write.xlsx";
        //传入文件路径和实体类名
        EasyExcel.write(filePath,DataDemo.class).sheet("学生列表").doWrite(getData());*/

        //文件路径
        String filePath = "E:\\write.xlsx";
        EasyExcel.read(filePath,DataDemo.class,new ExcelListener()).sheet().doRead();

    }

    public static List<DataDemo> getData(){
        ArrayList<DataDemo> list = new ArrayList<>();
        for (int i=0;i<10;i++) {
            DataDemo dataDemo = new DataDemo();
            dataDemo.setNo(i);
            dataDemo.setName("lili" + i);
            list.add(dataDemo);
        }
        return list;
    }


}
