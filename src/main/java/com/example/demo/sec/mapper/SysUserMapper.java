package com.example.demo.sec.mapper;

import com.example.demo.sec.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author shuxia
 * @since 2021-06-26
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
  //两个参数要用@Param，或者用#{arg0},#{arg1}占位
  List<SysUser> getUserLimit(@Param("page") Integer page, @Param("size") Integer size);
  List<SysUser> getUserBydeptIdLimit(@Param("deptId")Long deptId,@Param("page") Integer page, @Param("size") Integer size);

}
