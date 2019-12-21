package com.boyu.erp.platform.usercenter.controller.warehouse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("page/{name}")
    public String goPage(@PathVariable String name){
        return name;
    }
}
