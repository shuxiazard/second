package com.example.demo.entity;

import lombok.Data;

import java.math.BigInteger;

/**
 * AuthRole
 *
 * @author shuxia
 * @date 7/3/2021
 */
@Data
public class AuthRole {
    private Long userId;
    private Long roleId;
    private String roleName;
}
