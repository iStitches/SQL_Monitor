package com.xjx.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSqlAnalysis {
    // 主键ID
    private int id;
    // 全部参数
    private String allParameter;
    // 占位符对应的参数（实际用到的）
    private String usefulParameter;
    // 类型
    private String type;
    // mapper命名空间
    private String mapper;
    // 替换占位符之前的SQL语句
    private String initSqls;
    // 替换占位符之后的SQL语句
    private String finalSqls;
    // SQL性能分析
    private String analysis;
    // 执行SQL的时间
    private Date queryTime;
}
