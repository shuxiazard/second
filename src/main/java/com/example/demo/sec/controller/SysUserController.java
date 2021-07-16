package com.example.demo.sec.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.sec.entity.DepartTree;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.service.impl.RedisServiceImpl;
import com.example.demo.sec.service.impl.RedisTemplateObjectServiceImpl;
import com.example.demo.sec.service.impl.SysDeptServiceImpl;
import com.example.demo.sec.service.impl.SysUserServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author shuxia
 * @since 2021-06-26
 */
@Controller
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired(required = false)
    SysDeptServiceImpl sysDeptService;
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    RedisServiceImpl redisService;
    @Value("${redis.key.expire.authCode}")
    Long expire;

    @GetMapping("/dept")
    public String getDept(Model model) {
        final String dept = redisService.get("user:dept:");
        if (dept == null) {
            final List<DepartTree> departRoot = getChildren(sysDeptService.getDepartRoot());
            redisService.set("user:dept:", JSON.toJSONString(departRoot), expire);
            model.addAttribute("depart", departRoot);
        } else {
            model.addAttribute("depart", JSON.parseArray(dept, DepartTree.class));
        }
        return "user";
    }

    //根据部门显示员工
    @GetMapping("/table")
    @ResponseBody
    public HashMap<String,Object> getTableUserByDeptId(
            @RequestParam("deptId") Long deptId
            , @RequestParam("page") Integer page
            , @RequestParam("size") Integer size) {
        final HashMap<String,Object> map = new HashMap<>();
        final String allUser = redisService.get("user:deptId:"+page+":size:"+size+":");
        final String partUser = redisService.get("user:deptId:" + deptId + ":page:"+page+":"+"size:"+size+":");
        int count;
        if (deptId == -1) {
            final List<SysUser> allList = sysUserService.getUserLimit(page,size);
            count = sysUserService.count();
            map.put("count",count);
            if (allUser == null) {
                //放入redis缓存
                final String jsonString = JSON.toJSONString(allList);
                redisService.set("user:deptId:"+page+":", jsonString, expire);
               map.put("data",allList);
            return map;
            } else {
                final List<SysUser> sysUsers = JSON.parseArray(allUser, SysUser.class);
                 map.put("data", sysUsers);
                 return map;
            }
        } else if (partUser == null) {
            final QueryWrapper<SysUser> deptId1 = new QueryWrapper<SysUser>().eq("dept_id", deptId);
            final List<SysUser> partList = sysUserService.getUserLimit(deptId,page,size);
            count = sysUserService.count(deptId1);
            final String part = JSON.toJSONString(partList);
            redisService.set("user:deptId:" + deptId + ":page:"+page+":"+"size:"+size+":", part, expire);
            map.put("count",count);
            map.put("data",partList);
            return map;
        } else {
            final QueryWrapper<SysUser> deptId1 = new QueryWrapper<SysUser>().eq("dept_id", deptId);
            count = sysUserService.count(deptId1);
            final List<SysUser> sysUsers = JSON.parseArray(partUser, SysUser.class);
            map.put("count",count);
            map.put("data",sysUsers);
            return map;
        }
    }

    //搜索框
    @GetMapping("/table/userid")
    @ResponseBody
    public List<SysUser> getTableUserById(@RequestParam("userId") Long userId) {
        final String userCache = redisService.get("user:userId:" + userId + ":");
        //userid为空时返回全部
        if (userCache == null) {
            if (userId == null) {
                final List<SysUser> list = sysUserService.list();
                redisService.set("user:userId:" + userId + ":", JSON.toJSONString(list), expire);
                return list;
            } else {
                final QueryWrapper<SysUser> userById = new QueryWrapper<SysUser>().eq("user_id", userId);
                final List<SysUser> list = sysUserService.list(userById);
                redisService.set("user:userId:" + userId + ":", JSON.toJSONString(list), expire);
                return list;
            }
        }
        return JSON.parseArray(userCache, SysUser.class);
    }

    //编辑员工信息
    @PostMapping("/edit")
    @ResponseBody
    public boolean setEditUser(SysUser user) {
        return sysUserService.updateById(user);

    }

    //添加员工信息
    @PostMapping("/add")
    @ResponseBody
    public boolean addUser(SysUser user) {
        return sysUserService.save(user);
    }

    //删除员工信息
    @PostMapping("/delete")
    @ResponseBody
    public boolean deleteUser(Long userId) {
        return sysUserService.removeById(userId);
    }

    private List<DepartTree> getChildren(List<DepartTree> departRoot) {
        departRoot.stream().forEach(departTree -> {
            final Long deptId = departTree.getDeptId();
            final List<DepartTree> children = sysDeptService.getChildren(deptId);
            getChildren(children);
            departTree.setChildren(children);
        });
        return departRoot;
    }


}
