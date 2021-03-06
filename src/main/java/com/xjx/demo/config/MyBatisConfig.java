package com.xjx.demo.config;
import com.xjx.demo.interceptor.MyBatisInterceptor;
import com.xjx.demo.utils.ConfigsUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource,MyBatisInterceptor myBatisInterceptor) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));
        // 是否动态开启监控的开关
        String res = ConfigsUtil.getByValue(null, "monitor");
        if(ConfigsUtil.getByValue(null,"monitor").equals("1"))
            factoryBean.setPlugins(new Interceptor[]{myBatisInterceptor});
        return factoryBean.getObject();
    }
}
