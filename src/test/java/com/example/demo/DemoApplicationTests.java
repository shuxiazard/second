package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.sec.entity.SysUser;
import com.example.demo.sec.service.impl.RedisServiceImpl;
import com.example.demo.sec.service.impl.RedisTemplateObjectServiceImpl;
import com.example.demo.sec.service.impl.SysUserServiceImpl;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
  @Autowired
    RedisTemplateObjectServiceImpl redisTemplateObjectService;
  @Autowired
  SysUserServiceImpl sysUserService;
  @Autowired
  RedisServiceImpl redisService;
    @Test
    void contextLoads() {
      final List<SysUser> list = sysUserService.list();
      final String s = JSON.toJSONString(list);
      redisService.set("a",s);
      final String a = redisService.get("a");
      System.out.println(list);
      System.out.println(s);
      System.out.println(a);
      System.out.println(JSON.parseArray(a,SysUser.class));

      redisTemplateObjectService.set("b",list);
    }

}
