package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Hello
 *
 * @author 彭彭
 * @date 6/25/2021
 */
@Controller
public class Hello {

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }



}
