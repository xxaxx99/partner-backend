package com.lzh.partner.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description 测试类
 * @Version 1.0.0
 * @Date 2023/9/29 18:08
 * @Created by lzh
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String helloController(){
        return "hello";
    }

}
