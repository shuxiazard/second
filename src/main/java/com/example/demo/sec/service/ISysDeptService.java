package com.example.demo.sec.service;

import com.example.demo.sec.entity.DepartTree;
import com.example.demo.sec.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author shuxia
 * @since 2021-06-25
 */
public interface ISysDeptService extends IService<SysDept> {
   public List<DepartTree> getDepartRoot();
   public  List<DepartTree> getChildren(Long deptId);
}
