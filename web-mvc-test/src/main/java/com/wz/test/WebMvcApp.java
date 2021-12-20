package com.wz.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @projectName: wz-component-test
 * @package: com.wz.test
 * @className: WebMvcApp
 * @description:
 * @author: zhi
 * @date: 2021/12/20
 * @version: 1.0
 */
@MapperScan("com.wz.test.mapper")
@SpringBootApplication
public class WebMvcApp {

    public static void main(String[] args) {
        SpringApplication.run(WebMvcApp.class, args);
    }
}
