package com.example.demo.sec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.AdminDetails;
import com.example.demo.entity.AuthRole;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Value;
=======
>>>>>>> main
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
<<<<<<< HEAD
  @Value("${jwt.tokenHeader}")
  private String tokenHeader;
  @Value("${jwt.tokenHead}")
  private String tokenHead;
=======
>>>>>>> main

  @PostMapping("/index")
    public String getIndex(SysUser sysUser,Model model){
    final String token = sysUserService.login(sysUser.getLoginName(), sysUser.getPassword());
<<<<<<< HEAD

    model.addAttribute("msg","用户名或密码错误");
    final Map<String, String> map = new HashMap<>();
    map.put("token", token);
    map.put("tokenHead", tokenHead);
    map.put("tokenHeader",tokenHeader);
    model.addAllAttributes(map);
    System.out.println(token);
      return "redirect:index";
  }

  @GetMapping("/index")
  public String getIndex( Principal principal,Model model) {
    model.addAttribute("loginName",principal);
    System.out.println(principal);
    System.out.println(SecurityContextHolder.getContext().getAuthentication());
=======
    if (token==null){
      model.addAttribute("msg","用户名或密码错误");
    }
    model.addAttribute("loginName",sysUser.getLoginName());
    System.out.println(token);
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    return "redirect:/index";
  }
  @GetMapping("/index")
  @PreAuthorize("hasAuthority('admin')")
  public String getIndex( Model model) {

>>>>>>> main
    return "index";
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
