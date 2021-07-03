package com.example.demo.sec.controller;

import com.example.demo.entity.AuthRole;
import com.example.demo.sec.service.impl.AuthRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * AuthRoleController
 *
 * @author shuxia
 * @date 7/3/2021
 */
@Controller
public class AuthRoleController {
    @Autowired
    AuthRoleServiceImpl authRoleService;
    @GetMapping("/role")
    @ResponseBody
    public void getAuthRole(Long userId){
        final List<AuthRole> roleList = authRoleService.getRoleList(userId);
        for (AuthRole authRole : roleList) {
            System.out.println(authRole);
        }
    }
}
