package com.xjx.demo.service.impl;

import com.xjx.demo.dao.GoodsDao;
import com.xjx.demo.entity.Goods;
import com.xjx.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsDao goodsDao;

    @Override
    public Goods queryOne(int id) {
        return goodsDao.getById(id);
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsDao.getAll();
    }

    @Override
    public List<Goods> getByRangePrice() {
        return goodsDao.
    }

    @Override
    public void addOneGoods(Goods goods) {

    }

    @Override
    public void addManyGoods(List<Goods> goodsList) {

    }

    @Override
    public void deleteOne(int id) {

    }

    @Override
    public void deleteMany(List<Integer> idList) {

    }

    @Override
    public void updateOne(Goods goods) {

    }

    @Override
    public void updateMany(List<Goods> goodsList) {

    }
}
