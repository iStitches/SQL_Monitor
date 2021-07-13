package com.xjx.demo;

import com.xjx.demo.dao.GoodsDao;
import com.xjx.demo.dao.PersonDao;
import com.xjx.demo.entity.Goods;
import com.xjx.demo.entity.Person;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class DemoApplicationTests {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    PersonDao personDao;


    @Test
    void contextLoads() {
//        Goods goods = new Goods("小天才手表",43.1f,55.6,300L,200L,true,new Date());
//        List<Goods> all = goodsDao.getAll();
//        System.out.println(all);
//        HashMap<String ,Integer> map = new HashMap();
//        map.put("A",1);
//        map.put("B",2);
//        List<Goods> byRangePrice = goodsDao.getByRangePrice(2.2f, 44.44,12,map,new Person(1,"John","我是二年",23));
//        System.out.println(byRangePrice);

//        Goods goods = new Goods("旺仔小馒头",12.3f,22.22,300L,200L,true,new Date());
//        goodsDao.insertOne(goods);

//        List<Goods> goodsList = new ArrayList<>();
//        goodsList.add(new Goods("哇哈哈",3.5f,33.33,500L,100L,true,new Date()));
//        goodsList.add(new Goods("蓝牙耳机",23.5f,44.44,700L,400L,true,new Date()));
//        goodsList.add(new Goods("黑鲨4pro",2499.1f,5000.2,500L,100L,true,new Date()));
//        goodsList.add(new Goods("任天堂",2099.0f,4000.0,300L,200L,true,new Date()));
//        goodsDao.batchInsert(goodsList);

//        Goods goods = goodsDao.getById(1);
//        System.out.println("one:  "+goods);
//        List<Goods> goodsList = goodsDao.getAll();
//        System.out.println("all:  "+goodsList);
//          personDao.insert(new Person("unonoi","7856124"));
          goodsDao.getByRangePrice("qwer",46.2);
    }

}
