package com.xjx.demo.dao;

import com.xjx.demo.entity.SysSqlAnalysis;

import java.util.List;

public interface SqlAnalysisDao {
    /**
     * 插入一条SQL语句分析记录
     * @param sysSqlAnalysis
     */
    void insert(SysSqlAnalysis sysSqlAnalysis);

    /**
     * 获取所有记录
     * @return
     */
    List<SysSqlAnalysis> getAll();
}
