package com.example.demo.sec.service.impl;

import com.example.demo.sec.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * RedisServiceImpl
 *
 * @author shuxia
 * @date 7/5/2021
 */
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    @Override
    public void set(String key, String value,Long expire) {

        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key,expire,TimeUnit.MINUTES);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
    @Override
    public boolean expire(String key, long expire,TimeUnit timeUnit) {
        return redisTemplate.expire(key, expire, timeUnit);
    }
    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean isExits(String key) {
        final String s = redisTemplate.opsForValue().get(key);
        return s == null;
    }



}
