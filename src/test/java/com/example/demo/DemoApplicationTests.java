package com.example.demo;

import com.example.demo.sec.service.impl.RedisTemplateObjectServiceImpl;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
  @Autowired
    RedisTemplateObjectServiceImpl redisTemplateObjectService;
    @Test
    void contextLoads() {
        redisTemplateObjectService.set("a","asfa");
    }

}
