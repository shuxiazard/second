package com.example.demo.sec.service;

import com.example.demo.entity.AuthRole;

import java.util.List;

public interface IAuthRoleService {
    public List<AuthRole> getRoleList(Long usrId);
}
