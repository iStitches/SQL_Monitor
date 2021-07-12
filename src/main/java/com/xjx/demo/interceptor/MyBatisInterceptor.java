package com.xjx.demo.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjx.demo.entity.SysSqlAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;


/**
 * MyBatis拦截器,拦截SQL
 */
@Slf4j
@Component
@Intercepts({
//        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class,
//                Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class MyBatisInterceptor implements Interceptor {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object intercept(Invocation invocation) throws Exception{
        try {
            SysSqlAnalysis sqlAnalysis = new SysSqlAnalysis();
            // MappedStatement(获取XML中的结点,是一条SQL语句)
            MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
            // 封装所有的方法参数
            Object parameter = invocation.getArgs()[1];
            sqlAnalysis.setAllParameter(getAllParameter(parameter));
            // 封装方法对应的Mapper名称
            String mapperName = statement.getId();
            sqlAnalysis.setMapper(mapperName);
            // 获取方法操作类型(SELECT、UPDATE)
            String sqlCommandType = statement.getSqlCommandType().toString();
            sqlAnalysis.setType(sqlCommandType);

            // BoundSql：动态生成SQL语句及相关信息
            BoundSql boundSql = statement.getBoundSql(parameter);
            Configuration configuration = statement.getConfiguration();
            // 获取没有替换？的SQL语句
            String origSql = boundSql.getSql();
            sqlAnalysis.setInitSqls(origSql);
            String sql = getConcreteSql(sqlAnalysis,configuration,boundSql);
            sqlAnalysis.setFinalSqls(sql);
            log.info("==========SQL记录==========：{}",sqlAnalysis);
            log.info("准备记录到数据库...........");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 完成所有统计操作后,继续执行原来的SQL语句
        return invocation.proceed();
    }

    // 获取所有xxxDao中的参数
    public String getAllParameter(Object object) throws Exception{
        int i=1;
        LinkedHashMap<String,Object> ans = new LinkedHashMap();
        if(object instanceof Map)
        {
            Map map = (Map)object;
            while(map.containsKey("param"+i)){
                String key = "param"+i;
                ans.put(key,map.get(key));
                i++;
            }
        }
        return mapper.writeValueAsString(ans);
    }

    // 对SQL 进行处理(可以根据IP地址进行访问权限管理控制)
    public String getConcreteSql(SysSqlAnalysis sqlAnalysis, Configuration configuration, BoundSql boundSql) throws Exception{
        String sql = showSql(sqlAnalysis,configuration,boundSql);
        return sql;
    }

    // 获取完整的SQL语句(替换掉？)
    public String showSql(SysSqlAnalysis sqlAnalysis, Configuration configuration, BoundSql boundSql) throws Exception{
        String sql = boundSql.getSql();
        // 获取到xxxDao 方法参数(不一定全部是SQL中用到的参数)
        Object parameterObject = boundSql.getParameterObject();
        // 获取到真正SQL中用到的参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        MetaObject metaObject = configuration.newMetaObject(parameterObject);
        LinkedHashMap<String,Object> ans = new LinkedHashMap<>();
        // 替换SQL语句中的？
        for(ParameterMapping mapping:parameterMappings){
            String propertyName = mapping.getProperty();
            if(metaObject.hasGetter(propertyName)){
                ans.put(propertyName,metaObject.getValue(propertyName));
                Object obj = metaObject.getValue(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(dealwithParameter(obj)));
            }
        }
        // 将结果封装进实体对象中
        sqlAnalysis.setUsefulParameter(mapper.writeValueAsString(ans));
        return sql;
    }

    /**
     * 处理SQL语句中的参数：
     * 比如说 字符串/日期 在两端添加上单引号；日期转换为日期格式
     * @param object
     * @return
     */
    public String dealwithParameter(Object object){
        String ans = null;
        // 如果是字符串，两边添加单引号
        if(object instanceof String){
            ans = "'"+object.toString()+"'";
        }
        // 处理日期格式
        else if(object instanceof Date){
            String dateStr = ((Date)object).toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            LocalDate date = LocalDate.parse(dateStr,formatter);
            ans = "'"+formatter.format(date)+"'";
        }
        else{
            if(object != null)
                ans = object.toString();
            else
                ans = "";
        }
        return  ans;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }
}
