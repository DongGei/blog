package com.donggei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: MainController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/9/20
 **/
@Controller
public class MainController {

    @Deprecated
    @RequestMapping("/index")
    public String index(){
        return  "redirect:http://localhost:8989/index.html";
    }
}
