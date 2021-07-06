package com.example.demo.sec.service.impl;

import com.example.demo.sec.entity.SysDept;
import com.example.demo.sec.service.IRedisTemplateObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RedisTemplateObjectServiceImpl
 *
 * @author shuxia
 * @date 7/5/2021
 */
@Service
public class RedisTemplateObjectServiceImpl implements IRedisTemplateObjectService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.opsForSet().remove(key);
    }

    @Override
    public boolean isExits(String key) {
        final Object s = redisTemplate.opsForSet().members(key);
        return s == null;
    }
}
