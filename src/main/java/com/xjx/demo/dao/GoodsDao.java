package com.xjx.demo.dao;

import com.xjx.demo.entity.Goods;

import java.util.List;

public interface GoodsDao {
    /**
     * 单个查询(带参数)
     * @param id
     * @return
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "getById")
    Goods getById(int id, String password, String username);
    /**
     * 集合查询
     * @return
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "getAll")
    List<Goods> getAll();
    /**
     * 根据价格范围获取商品集合(多个参数查询)
     * @param minPrice
     * @param maxPrice
     * @return
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "getByRangePrice")
    List<Goods> getByRangePrice(String minPrice, double maxPrice);

    /**
     * 单个插入
     * @param goods
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "insertOne")
    void insertOne(Goods goods);

    /**
     * 批量插入
     * @param goodsList
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "batchInsert")
    void batchInsert(List<Goods> goodsList);

    /**
     * 单个删除
     * @param id
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "deleteOne")
    void deleteOne(int id);

    /**
     * 批量删除
     * @param idList
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "batchDelete")
    void batchDelete(List<Integer> idList);

    /**
     * 更新
     * @param goods
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "update")
    void update(Goods goods);

    /**
     * 批量更新
     * @param goodsList
     */
//    @LogRecord(location = "com.xjx.demo.dao.GoodsDao",methodName = "batchUpdate")
    void batchUpdate(List<Goods> goodsList);
}
