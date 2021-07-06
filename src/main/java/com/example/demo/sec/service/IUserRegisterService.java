package com.example.demo.sec.service;

public interface IUserRegisterService {
    String generateAuthCode(String telephone);

    boolean verifyAuthCode(String telephone, String authCode);

    boolean isExist(String telphone);
    boolean count(String telphone);
}
