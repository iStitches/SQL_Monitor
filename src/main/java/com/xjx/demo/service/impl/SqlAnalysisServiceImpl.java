package com.xjx.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjx.demo.dao.SqlAnalysisDao;
import com.xjx.demo.entity.SysSqlAnalysis;
import com.xjx.demo.service.SqlAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlAnalysisServiceImpl implements SqlAnalysisService {
    @Autowired
    SqlAnalysisDao sqlAnalysisDao;

    @Override
    public void insert(SysSqlAnalysis sysSqlAnalysis) {
        sqlAnalysisDao.insert(sysSqlAnalysis);
    }

    @Override
    public List<SysSqlAnalysis> getAll() {
        return sqlAnalysisDao.getAll();
    }

    @Override
    public PageInfo<SysSqlAnalysis> getByPage(int pageSize, int pageNum) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum,pageSize);
        List<SysSqlAnalysis> all = sqlAnalysisDao.getAll();
        PageInfo<SysSqlAnalysis> ans = new PageInfo<>(all);
        return ans;
    }
}
