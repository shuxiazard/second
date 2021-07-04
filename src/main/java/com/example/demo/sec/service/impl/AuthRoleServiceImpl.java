package com.example.demo.sec.service.impl;

import com.example.demo.entity.AuthRole;
import com.example.demo.sec.mapper.AuthRoleMapper;
import com.example.demo.sec.service.IAuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IAuthRoleService
 *
 * @author shuxia
 * @date 7/3/2021
 */
@Service
public class AuthRoleServiceImpl implements IAuthRoleService {
    @Autowired
    AuthRoleMapper authRoleMapper;
    @Override
    public List<AuthRole> getRoleList(Long userId) {
        final List<AuthRole> roleList = authRoleMapper.getRoleList(userId);
        return roleList;
    }
}
