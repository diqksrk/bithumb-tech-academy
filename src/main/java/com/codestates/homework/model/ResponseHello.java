package com.codestates.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ResponseHello {
    public String to;
    public String job;
    public String message;

    public ResponseHello(String to, String job) {
        this.to = to;
        this.message = "hello " + to;
        this.job = job;
    }
}
