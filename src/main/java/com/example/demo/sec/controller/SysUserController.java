package com.example.demo.sec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.sec.entity.DepartTree;
import com.example.demo.sec.entity.SysDept;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.service.impl.SysDeptServiceImpl;
import com.example.demo.sec.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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

    @GetMapping("/dept")
    public String  getDept(Model model) {
        final List<SysDept> list = sysDeptService.list();
        final List<DepartTree> departRoot = getChildren( sysDeptService.getDepartRoot());
        model.addAttribute("departs",departRoot);
        return "user";

    }

    //根据部门显示员工
    @GetMapping("/table")
    @ResponseBody
    public List<SysUser> getTableUserByDeptId(@RequestParam("deptId") Long deptId){
        if (deptId==-1){
            final List<SysUser> list = sysUserService.list();
            return  list;
        }
        final QueryWrapper<SysUser> deptId1 = new QueryWrapper<SysUser>().eq("dept_id", deptId);
        final List<SysUser> list = sysUserService.list(deptId1);
        return list;

    }

    //搜索框
    @GetMapping("/table/userid")
    @ResponseBody
    public List<SysUser> getTableUserById(@RequestParam("userId") Long userId){
        //userid为空时返回全部
        if (userId==null){
            final List<SysUser> list = sysUserService.list();
            return  list;
        }
        final QueryWrapper<SysUser> userById = new QueryWrapper<SysUser>().eq("user_id", userId);
        final List<SysUser> list = sysUserService.list(userById);
        return list;

    }

    //编辑员工信息
   @PostMapping("/edit")
   @ResponseBody
    public boolean setEditUser(SysUser user){
       return sysUserService.updateById(user);

   }

   //添加员工信息
    @PostMapping("/add")
    @ResponseBody
    public boolean addUser(SysUser user){
        return sysUserService.save(user);
    }

    //删除员工信息
    @PostMapping("/delete")
    @ResponseBody
    public  boolean deleteUser(Long userId){
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
