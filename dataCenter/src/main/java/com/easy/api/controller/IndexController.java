package com.easy.api.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * @author muchi
 **/
@Controller
public class IndexController
{

    @RequestMapping("/admin")
    public String index() {
        return "index";
    }

}