package com.example.demo.sec.mapper;

import com.example.demo.entity.AuthRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthRoleMapper {
    List<AuthRole> getRoleList(Long userId);
}
