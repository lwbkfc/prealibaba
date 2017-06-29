package com.lin.alibaba;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lwb on 2017/6/24.
 */
@Controller
public class MultiThreadController {
    private static int i = 100;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println("current thread"+Thread.currentThread().getId());
        i = i-3;
        return String.valueOf(i);
    }


}
