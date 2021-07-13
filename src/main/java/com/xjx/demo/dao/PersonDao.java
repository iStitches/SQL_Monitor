package com.xjx.demo.dao;

import com.xjx.demo.entity.Person;

public interface PersonDao {
    void insert(Person person);

    void getAll();

    void getById(int id);
}
