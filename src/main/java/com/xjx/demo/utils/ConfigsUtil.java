package com.xjx.demo.utils;


import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析文件的工具类
 */
public class ConfigsUtil {
    // 默认文件(主配置文件)
    private static String DEFAULT_FILE = "application.yaml";
    // 一般键值对结果
    private static Map<String,Object> result = new HashMap<>();

    /**
     * 根据文件名获取yml的文件内容
     * @param file
     * @return
     */
    public static Map<String,Object> getYamlByFileName(String file){
        if(file == null){
            file = DEFAULT_FILE;
        }
        // 获取文件流
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        Yaml yaml = new Yaml();
        Object obj = yaml.loadAs(inputStream,Map.class);
        Map<String,Object> params = (Map<String, Object>)obj;
        // 解析结果
        for(Map.Entry<String,Object> entry:params.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            result.put(key,value);
        }
        return result;
    }

    /**
     * 获取指定字段的结果
     * @param key
     * @return
     */
    public static String getByValue(String fileName, String key){
        Map<String,Object> ans = getYamlByFileName(fileName);
        int a = 2;
        if(ans == null)
            return null;
        return ans.get(key).toString();
    }
}
