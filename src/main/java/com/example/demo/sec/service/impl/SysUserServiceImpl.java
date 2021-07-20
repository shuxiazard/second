package com.example.demo.sec.service.impl;


import com.example.demo.component.utils.JwtUtils;
import com.example.demo.entity.AdminDetails;
import com.example.demo.sec.entity.SysActived;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.mapper.SysUserMapper;
import com.example.demo.sec.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysActivedServiceImpl sysActivedService;

    @Override
    public String login(String loginName, String password) {
        String token=null;
        final AdminDetails userDetails = myUserDetailService.loadUserByUsername(loginName);
        //如果用户名不存在
        if (userDetails==null){
            return token;
        }
        final boolean matches = passwordEncoder.matches(password, userDetails.getPassword());
        if (matches){
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            final SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(emptyContext);
           token=jwtUtils.generalToken(userDetails.getUsername());

        } else {
            throw new BadCredentialsException("密码或用户名错误");
        }
        return token;
    }

    @Override
    public List<SysUser> getUserLimit(Integer page, Integer size) {
        return sysUserMapper.getUserLimit((page - 1) * size, size);
    }

    @Override
    public List<SysUser> getUserLimit(Long deptId, Integer page, Integer size) {
        return sysUserMapper.getUserBydeptIdLimit(deptId,(page - 1) * size, size);
    }
}
