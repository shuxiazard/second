package com.example.demo.sec.service;

import com.example.demo.sec.entity.DepartTree;

import java.util.List;

public interface IRedisTemplateObjectService {
    void set(String key, Object value);

    Object get(String key);


    void remove(String key);

    boolean isExits(String key);

    void set(String key, Object value, Long expirt);
}
