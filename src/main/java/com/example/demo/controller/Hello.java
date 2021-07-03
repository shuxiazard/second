package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Hello
 *
 * @author 彭彭
 * @date 6/25/2021
 */
@Controller
public class Hello {

    @GetMapping("/index")
    public String getIndex( Principal principal,Model model) {
                 model.addAttribute("loginName",principal.getName());
        return "index";
    }



}
