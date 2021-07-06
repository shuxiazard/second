package com.example.demo.sec.service;

public interface IRedisTemplateObjectService {
    void set(String key, Object value);

    Object get(String key);


    void remove(String key);

    boolean isExits(String key);
}
