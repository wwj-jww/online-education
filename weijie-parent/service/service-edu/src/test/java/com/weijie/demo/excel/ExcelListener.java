package com.weijie.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DataDemo> {
    //一行一行读取数据
    @Override
    public void invoke(DataDemo dataDemo, AnalysisContext analysisContext) {
        System.out.println(dataDemo);
    }
    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }
    //读取完成后执行的方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
