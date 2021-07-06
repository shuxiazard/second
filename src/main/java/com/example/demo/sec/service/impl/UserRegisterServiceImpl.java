package com.example.demo.sec.service.impl;

import com.example.demo.sec.service.IUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * UserRegisterServiceImpl
 *
 * @author shuxia
 * @date 7/5/2021
 */
@Service
public class UserRegisterServiceImpl implements IUserRegisterService {
    @Autowired
    RedisServiceImpl redisService;
    //key前缀
    @Value("${redis.key.prefix.authCode}")
    String authCode;
    //期望时间
    @Value("${redis.key.expire.authCode}")
    Long expire;
    //统计验证次数
    @Value("${redis.key.count.authCode}")
    String count;

    @Override
    public String generateAuthCode(String telephone) {
        final StringBuilder stringBuilder = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < 6; i++) {
            final int c = random.nextInt(10);
            stringBuilder.append(c);
        }
        final String s = stringBuilder.toString();

        //放到缓存
        redisService.set(authCode + telephone, s);
        //设置期望时间
        redisService.expire(authCode + telephone, expire);
        return s;

    }

    @Override
    public boolean verifyAuthCode(String telephone, String authCode) {
        final String s = redisService.get(this.authCode + telephone);
        if (s == null) {
            return false;
        }

        return s.equals(authCode);
    }

    @Override
    public boolean isExist(String telphone) {
        return redisService.isExits(this.authCode + telphone);
    }

    @Override
    public boolean count(String telphone) {
        final String s = redisService.get(count + telphone);
        if (s == null) {
            redisService.set(count + telphone, "1");
            return false;
        }
        final Long increment = redisService.redisTemplate.opsForValue().increment(count + telphone);
        return increment > 3;
    }
}
