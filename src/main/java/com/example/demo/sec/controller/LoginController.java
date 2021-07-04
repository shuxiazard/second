package com.example.demo.sec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.AdminDetails;
import com.example.demo.entity.AuthRole;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * LoginController
 *
 * @author shuxia
 * @date 7/3/2021
 */
@Controller
public class LoginController {
  @Autowired
  SysUserServiceImpl sysUserService;
  BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
  @PostMapping("/index")
    public String getIndex(SysUser sysUser,Model model){
    final String user = sysUserService.login(sysUser.getLoginName(), sysUser.getPassword());
    if (user!=null){
      return "redirect:/index";
    }
    model.addAttribute("msg","用户名或密码错误");
      return "login";
  }

  @GetMapping("/register")
  public String register(){
    return "register";
  }

  @PostMapping("register")
  public String saveUser(SysUser sysUser, Model model){
    SysUser user = getUserByName(sysUser.getLoginName());
    if (user!=null){
        model.addAttribute("msg","注册失败，用户名已存在");
      return "/register";
    }
    //加密
    final String encode = bCryptPasswordEncoder.encode(sysUser.getPassword());
    sysUser.setPassword(encode);
    model.addAttribute("msg","注册成功");
    sysUserService.save(sysUser);
    return "/login";
  }

  public SysUser getUserByName(String loginName){
    final QueryWrapper<SysUser> qw = new QueryWrapper<>();
    qw.eq("login_name",loginName);
    return  sysUserService.getOne(qw);
  }
}
