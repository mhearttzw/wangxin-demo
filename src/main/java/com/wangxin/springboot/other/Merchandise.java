package com.wangxin.springboot.other;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//value注解实验
@Component
@ConfigurationProperties(prefix = "product")
public class Merchandise {

    private String car;

    private String bed;

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }
}
