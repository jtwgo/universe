package com.jtw.main;

import com.jtw.main.service.IconsumerInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Consumer
{
    @Autowired
    private IconsumerInter iconsumerInter;

    @GetMapping("/hello")
    @ResponseBody
    public String hello()
    {
        return  iconsumerInter.hello();
    }



}
