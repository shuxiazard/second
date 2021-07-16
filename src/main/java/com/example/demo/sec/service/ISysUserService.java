package com.example.demo.sec.service;

import com.example.demo.sec.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author shuxia
 * @since 2021-06-26
 */
public interface ISysUserService extends IService<SysUser> {
    String login(String username,String password);
    List<SysUser> getUserLimit(Integer page, Integer size);
    List<SysUser> getUserLimit(Long deptId,Integer page, Integer size);
}
