package com.codestates.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ResponseHello {
    public String to;
    public String message;

    public ResponseHello(String to) {
        this.to = to;
        this.message = "hello " + to;
    }
}
