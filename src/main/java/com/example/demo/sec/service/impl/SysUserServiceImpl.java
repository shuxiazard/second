package com.example.demo.sec.service.impl;

import com.example.demo.entity.AdminDetails;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.mapper.SysUserMapper;
import com.example.demo.sec.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author shuxia
 * @since 2021-06-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
   @Autowired
 MyUserDetailServiceImpl myUserDetailService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public String login(String loginName, String password) {
        final AdminDetails userDetails = myUserDetailService.loadUserByUsername(loginName);
        final boolean matches = passwordEncoder.matches(password, userDetails.getPassword());
        if (matches){
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            final SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(emptyContext);
            return loginName;
        }
        return null;
    }
}
