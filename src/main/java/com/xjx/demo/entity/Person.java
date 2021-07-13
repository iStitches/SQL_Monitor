package com.xjx.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    private String userName;
    private String password;

    public Person(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
