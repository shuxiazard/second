package com.example.demo.sec.service;


import java.util.concurrent.TimeUnit;

public interface IRedisService {
    void set(String key, String value);

    String get(String key);

    boolean expire(String key, long expire);

    void remove(String key);

    boolean isExits(String key);
    public void set(String key, String value,Long expire);
    public boolean expire(String key, long expire, TimeUnit timeUnit);
}
