package com.example.demo.sec.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.sec.entity.SysDept;
import com.example.demo.sec.service.impl.RedisServiceImpl;
import com.example.demo.sec.service.impl.SysDeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author shuxia
 * @since 2021-06-25
 */
@Controller
@RequestMapping("/dept")
public class SysDeptController {
    @Autowired
    SysDeptServiceImpl sysDeptService;
    @Autowired
    RedisServiceImpl redisService;
    @Value("${redis.key.expire.authCode}")
    Long expire;

    //查询所有部门
    @GetMapping("")
    public String getAllDept(Model model) {
        //查询缓存
        final String allList = redisService.get("dept:all:");
        if (allList == null) {
            final List<SysDept> list = sysDeptService.list();
            final String departCache = JSON.toJSONString(list);
            redisService.set("dept:all:", departCache, expire);
            model.addAttribute("dept",list);
            return "depart";
        } else {
            final List<SysDept> sysDepts = JSON.parseArray(allList, SysDept.class);
            model.addAttribute("dept",sysDepts);
            return "depart";
        }
    }

    //根据ID查询部门
    @GetMapping("/deptId")
    @ResponseBody
    public List<SysDept> getDeptById(Long deptId) {
        //查询缓存
        final String deptById = redisService.get("dept:deptId:" + deptId + ":");
        if (deptById == null) {
            final QueryWrapper<SysDept> qw = new QueryWrapper<>();
            qw.eq("dept_id", deptId);
            final List<SysDept> partList = sysDeptService.list(qw);
            redisService.set("dept:deptId:" + deptId + ":", JSON.toJSONString(partList), expire);
            return partList;
        } else {
            return JSON.parseArray(deptById, SysDept.class);
        }
    }

    //修改部门信息
    @PutMapping("")
    @ResponseBody
    public boolean setDept(SysDept sysDept){
        if (sysDept==null){
            return  false;
        }
        //更新信息
       return sysDeptService.updateById(sysDept);
    }
    //删除部门信息
    @DeleteMapping("")
    @ResponseBody
    public boolean delete(Long deptId){
        return sysDeptService.removeById(deptId);
    }

}
