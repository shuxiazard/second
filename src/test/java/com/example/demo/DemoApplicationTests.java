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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    }


    @Test
  void createData(){
      final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      final Random random = new Random();
      for (int i = 0; i <1000000 ; i++) {
        final SysUser sysUser = new SysUser();
     //   &updateBy=demoData&remark=demoData
        Long i1 = (long)random.nextInt(5)+103;
        sysUser.setDeptId(i1);
        sysUser.setLoginName("login"+i);
        sysUser.setUserName("user"+i);
        sysUser.setUserType("00");
        sysUser.setEmail(i+"@163.com");
        sysUser.setPhonenumber("12345678910");
        sysUser.setPassword(encoder.encode(""+i));
        sysUser.setSex(i%2==0?"男":"女");
        sysUser.setCreateBy("admin");
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setRemark("测试员");
       sysUserService.save(sysUser);
      }
    }

    @Test
  void testLimit(){
      final List<SysUser> userLimit = sysUserService.getUserLimit(1, 100);
      userLimit.forEach(System.out::println);
        System.out.println(LocalDate.now());
    }



}
