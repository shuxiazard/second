package com.example.demo.sec.service;


import java.util.concurrent.TimeUnit;

public interface IRedisService {
    void set(String key, String value);

    String get(String key);

    boolean expire(String key, long expire);

    void remove(String key);

    boolean isExits(String key);
    void set(String key, String value,Long expire);
    boolean expire(String key, long expire, TimeUnit timeUnit);
    void setBit(String key,Long userId);
    boolean getBit(String key,Long userId);
    //统计登录
    //Integer countBit(String key,Long)
}
