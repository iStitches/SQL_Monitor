package com.xjx.demo.dao;

import com.xjx.demo.annotation.SqlError;
import com.xjx.demo.entity.Goods;
import java.util.List;

public interface GoodsDao {
    /**
     * 单个查询(带参数)
     * @param id
     * @return
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.getById",description = "根据商品ID获取商品信息")
    Goods getById(int id, String password, String username);
    /**
     * 集合查询
     * @return
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.getAll",description = "获取全部商品信息")
    List<Goods> getAll();
    /**
     * 根据价格范围获取商品集合(多个参数查询)
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.getByRangePrice",description = "范围获取商品信息")
    List<Goods> getByRangePrice(float minPrice, double maxPrice);

    /**
     * 单个插入
     * @param goods
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.insertOne",description = "单个插入商品信息")
    void insertOne(Goods goods);

    /**
     * 批量插入
     * @param goodsList
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.batchInsert",description = "批量插入商品信息")
    void batchInsert(List<Goods> goodsList);

    /**
     * 单个删除
     * @param id
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.deleteOne",description = "根据ID删除单个信息")
    void deleteOne(int id);

    /**
     * 批量删除
     * @param idList
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.batchDelete",description = "批量删除商品信息")
    void batchDelete(List<Integer> idList);

    /**
     * 更新
     * @param goods
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.update",description = "更新商品信息")
    void update(Goods goods);

    /**
     * 批量更新
     * @param goodsList
     */
    @SqlError(methodName = "com.xjx.demo.dao.GoodsDao.batchUpdate",description = "批量更新商品信息")
    void batchUpdate(List<Goods> goodsList);
}
