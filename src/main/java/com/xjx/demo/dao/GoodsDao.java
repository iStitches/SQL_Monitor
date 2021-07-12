package com.xjx.demo.dao;

import com.xjx.demo.entity.Goods;
import com.xjx.demo.entity.Person;
import java.util.List;
import java.util.Map;

public interface GoodsDao {
    /**
     * 单个查询(带参数)
     * @param id
     * @return
     */
    Goods getById(int id);
    /**
     * 集合查询
     * @return
     */
    List<Goods> getAll();
    /**
     * 根据价格范围获取商品集合(多个参数查询)
     * @param minPrice
     * @param maxPrice
     * @return
     */
    List<Goods> getByRangePrice(float minPrice, double maxPrice, int age, Map<String,Integer> map, Person person);

    /**
     * 单个插入
     * @param goods
     */
    void insertOne(Goods goods);

    /**
     * 批量插入
     * @param goodsList
     */
    void batchInsert(List<Goods> goodsList);

    /**
     * 单个删除
     * @param id
     */
    void deleteOne(int id);

    /**
     * 批量删除
     * @param idList
     */
    void batchDelete(List<Integer> idList);

    /**
     * 更新
     * @param goods
     */
    void update(Goods goods);

    /**
     * 批量更新
     * @param goodsList
     */
    void batchUpdate(List<Goods> goodsList);
}
