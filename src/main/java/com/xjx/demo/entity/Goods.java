package com.xjx.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int id;
    private String name;
    private String minPrice;
    private double maxPrice;
    private long stores;
    private long restNumber;
    private boolean isSale;
    private Date stockTime;

    public Goods(String name, String minPrice, double maxPrice, long stores, long restNumber, boolean isSale, Date stockTime) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.stores = stores;
        this.restNumber = restNumber;
        this.isSale = isSale;
        this.stockTime = stockTime;
    }
}
