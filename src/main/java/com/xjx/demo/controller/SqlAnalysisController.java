package com.xjx.demo.controller;

import com.github.pagehelper.PageInfo;
import com.xjx.demo.constant.ResultObj;
import com.xjx.demo.entity.SysSqlAnalysis;
import com.xjx.demo.service.SqlAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sql")
public class SqlAnalysisController {
    @Autowired
    SqlAnalysisService sqlAnalysisService;

    @GetMapping("/getOnePage")
    public ResultObj getByPage(@RequestParam("pageSize") Integer pageSize,
                               @RequestParam("pageNum") Integer pageNum){
        PageInfo<SysSqlAnalysis> ans = sqlAnalysisService.getByPage(pageSize, pageNum);
        return ResultObj.success(ans);
    }
}
