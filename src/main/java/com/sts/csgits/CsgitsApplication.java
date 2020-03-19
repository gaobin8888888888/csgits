package com.sts.csgits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CsgitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsgitsApplication.class, args);
    }

}
