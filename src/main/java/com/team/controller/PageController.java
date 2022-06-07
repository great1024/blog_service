package com.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("page")
@Controller
public class PageController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("")
    public String goIndex(){
        return "index";
    }
    @GetMapping("content/list")
    public String contentList(){
        return "content-list";
    }
}
