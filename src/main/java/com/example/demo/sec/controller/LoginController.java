package com.example.demo.sec.controller;

import com.example.demo.sec.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * LoginController
 *
 * @author shuxia
 * @date 7/3/2021
 */
@Controller
public class LoginController {
  @PostMapping("/userlogin")
    public String getIndex(SysUser sysUser){
      return "index";
  }
}
