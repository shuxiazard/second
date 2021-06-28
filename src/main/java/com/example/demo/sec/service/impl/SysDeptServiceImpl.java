package com.example.demo.sec.service.impl;

import com.example.demo.sec.entity.DepartTree;
import com.example.demo.sec.entity.SysDept;
import com.example.demo.sec.mapper.SysDeptMapper;
import com.example.demo.sec.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author shuxia
 * @since 2021-06-25
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
   @Autowired(required = false)
   SysDeptMapper sysDeptMapper;
    @Override
    public List<DepartTree> getDepartRoot(){
       return sysDeptMapper.getDepartRoot();
    }

    @Override
    public List<DepartTree> getChildren(Long deptId) {
        return sysDeptMapper.getChildren(deptId);
    }

    ;


}
