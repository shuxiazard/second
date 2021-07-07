package com.example.demo.sec.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.sec.entity.SysDept;
import com.example.demo.sec.entity.SysRole;
import com.example.demo.sec.service.impl.RedisServiceImpl;
import com.example.demo.sec.service.impl.SysRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author shuxia
 * @since 2021-07-07
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    SysRoleServiceImpl sysRoleService;
    @Autowired
    RedisServiceImpl redisService;
    @Value("${redis.key.expire.authCode}")
    Long expire;

    //查询全部权限
    @GetMapping("/")
    public List<SysRole> getRole() {
        //缓存获取
        final String allList = redisService.get("role:all:");
        if (allList == null) {
            final List<SysRole> list = sysRoleService.list();
            final String s = JSON.toJSONString(list);
            redisService.set("role:all:", s, expire);
            return list;
        }
        return JSON.parseArray(allList, SysRole.class);
    }

    //根据id查询权限
    @GetMapping("/roleId")
    public List<SysRole> getRoleById(Long roleId) {
        //缓存
        final String partList = redisService.get("role:part:roleId" + roleId + ":");
        if (partList == null) {
            final QueryWrapper<SysRole> qw = new QueryWrapper<>();
            qw.eq("role_id", roleId);
            final List<SysRole> list = sysRoleService.list(qw);
            redisService.set("role:part:roleId:" + roleId + ":", JSON.toJSONString(list), expire);
            return list;
        }
        return JSON.parseArray(partList, SysRole.class);
    }

    //删除权限
    @DeleteMapping("")
    public boolean deleteRole(Long roleId) {
        if (roleId == null) {
            return false;
        }
        redisService.remove("role:part:roleId:" + roleId + ":");
        return sysRoleService.removeById(roleId);
    }
}
