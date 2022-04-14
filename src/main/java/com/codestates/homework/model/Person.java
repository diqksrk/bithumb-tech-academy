package com.codestates.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Person {
    private String name;
    private String email;
    private String number;

    public void changeToUpperName() {
        this.name = name.toUpperCase();
    }
}
