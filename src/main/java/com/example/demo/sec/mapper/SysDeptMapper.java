package com.example.demo.sec.mapper;

import com.example.demo.sec.entity.DepartTree;
import com.example.demo.sec.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author shuxia
 * @since 2021-06-25
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    List<DepartTree> getDepartRoot();
    List<DepartTree> getChildren(Long deptId);
}
