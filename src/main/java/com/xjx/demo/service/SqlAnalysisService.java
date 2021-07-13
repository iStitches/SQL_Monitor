package com.xjx.demo.service;

import com.github.pagehelper.PageInfo;
import com.xjx.demo.entity.SysSqlAnalysis;

import java.util.List;

public interface SqlAnalysisService {
    void insert(SysSqlAnalysis sysSqlAnalysis);

    List<SysSqlAnalysis> getAll();

    PageInfo<SysSqlAnalysis> getByPage(int pageSize, int pageNum);
}
