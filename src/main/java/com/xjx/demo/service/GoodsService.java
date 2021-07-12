package com.xjx.demo.service;

import com.xjx.demo.entity.Goods;

import java.util.List;

public interface GoodsService {
    Goods queryOne(int id);

    List<Goods> getAllGoods();

    List<Goods> getByRangePrice(float minPrice, double maxPrice);

    void addOneGoods(Goods goods);

    void addManyGoods(List<Goods> goodsList);

    void deleteOne(int id);

    void deleteMany(List<Integer> idList);

    void updateOne(Goods goods);

    void updateMany(List<Goods> goodsList);
}
