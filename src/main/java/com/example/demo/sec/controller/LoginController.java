package com.example.demo.sec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.service.impl.SysUserServiceImpl;
import com.example.demo.sec.service.impl.UserRegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRegisterServiceImpl userRegisterService;


    @PostMapping("/index")
    public String getIndex(SysUser sysUser, Model model) {
        final String token = sysUserService.login(sysUser.getLoginName(), sysUser.getPassword());
        if (token == null) {
            model.addAttribute("msg", "用户名或密码错误");
        }
        model.addAttribute("loginName", sysUser.getLoginName());
        System.out.println(token);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "redirect:/index";
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('admin')")
    public String getIndex(Model model) {

        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String saveUser(SysUser sysUser, Model model) {
        SysUser user = getUserByName(sysUser.getLoginName());
        if (user != null) {
            model.addAttribute("msg", "注册失败，用户名已存在");
            return "/register";
        }
        //加密
        final String encode = bCryptPasswordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encode);
        model.addAttribute("msg", "注册成功");
        sysUserService.save(sysUser);
        return "/login";
    }

    //获取验证码
    @GetMapping("/getAuthCode")
    @ResponseBody
    public String getAuthcode(String phoneNumber) {
        if (!userRegisterService.isExist(phoneNumber)) {
            return "验证码已存在，请稍后再发送";
        }
        return userRegisterService.generateAuthCode(phoneNumber);
    }

    //验证验证码
    @GetMapping("/verifyAuthCode")
    @ResponseBody
    public boolean verifyAuthCode(String phoneNumber, String authCode) {
        if (phoneNumber == null || authCode == null) {
            return false;
        }
        return userRegisterService.verifyAuthCode(phoneNumber, authCode);

    }

    public SysUser getUserByName(String loginName) {
        final QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("login_name", loginName);
        return sysUserService.getOne(qw);
    }

}
