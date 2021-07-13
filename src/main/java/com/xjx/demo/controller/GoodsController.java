package com.xjx.demo.controller;

import com.xjx.demo.constant.Common;
import com.xjx.demo.constant.ResultObj;
import com.xjx.demo.dao.PersonDao;
import com.xjx.demo.entity.Goods;
import com.xjx.demo.entity.Person;
import com.xjx.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    PersonDao personDao;

    @GetMapping("/all")
    public ResultObj getAll(){
        List<Goods> allGoods = goodsService.getAllGoods();
        return ResultObj.success(allGoods);
    }

    @GetMapping("/one")
    public ResultObj getOne(@RequestParam("id")Integer id){
        Goods goods = goodsService.queryOne(id);
        return ResultObj.success(goods);
    }

    @GetMapping("/queryByRangePrice")
    public ResultObj getRange(@RequestParam("minPrice") float minPrice,
                              @RequestParam("maxPrice") double maxPrice){
        goodsService.getByRangePrice(minPrice,maxPrice);
        return ResultObj.success();
    }

    @PostMapping("/addOne")
    public ResultObj addGoods(@RequestBody Goods goods){
        if(goods != null)
            goodsService.addOneGoods(goods);
        return ResultObj.success();
    }

    @PostMapping("/addMany")
    public ResultObj addGoodsList(@RequestBody List<Goods> goodsList){
        if(goodsList.size() > 0){
            goodsService.addManyGoods(goodsList);
        }
        return ResultObj.success();
    }

    @GetMapping("/deleteOne")
    public ResultObj deleteOneGoods(@RequestParam("id") Integer id){
        Goods ans = goodsService.queryOne(id);
        if(ans != null){
            goodsService.deleteOne(id);
            return ResultObj.success("成功删除！");
        }
        else {
            return ResultObj.failure(Common.GOODS_NOT_FOUND);
        }
    }

    @GetMapping("/deleteMany")
    public ResultObj deleteMany(@RequestBody List<Integer> idList){
        if(idList.size() >  0){
            goodsService.deleteMany(idList);
        }
        return ResultObj.success();
    }

    @PostMapping("/updateOne")
    public ResultObj updateOne(@RequestBody Goods goods){
        if(goods != null)
            goodsService.updateOne(goods);
        return ResultObj.success();
    }

    @PostMapping("/updateMany")
    public ResultObj updateMany(@RequestBody List<Goods> goodsList){
        if(goodsList.size() > 0)
            goodsService.updateMany(goodsList);
        return ResultObj.success();
    }

    @PostMapping("/addPerson")
    public ResultObj addPerson(@RequestBody Person person){
        personDao.insert(new Person("yybub","12sdfsd7qe"));
        return ResultObj.success();
    }
}
