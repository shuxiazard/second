package com.example.demo.sec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.AdminDetails;
import com.example.demo.entity.AuthRole;
import com.example.demo.sec.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MyUserDetailServiceImpl
 *
 * @author shuxia
 * @date 7/3/2021
 */
@Service
public class MyUserDetailServiceImpl  implements UserDetailsService {
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    AuthRoleServiceImpl authRoleService;
    @Override
    public AdminDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("login_name",username);
        final SysUser user = sysUserService.getOne(qw);
        if (user!=null){
            final List<AuthRole> roleList = authRoleService.getRoleList(user.getUserId());
            return new AdminDetails(user,roleList);
        }

        return null;
    }
}
