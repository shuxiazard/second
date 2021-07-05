package com.example.demo.common.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * JwtUtils
 *
 * @author shuxia
 * @date 7/4/2021
 */
@Component
public class JwtUtils {
    @Value("${jwt.expired}")
    private Long expired;
    static SecretKey secretKey;
    static {
        secretKey=getSecretKey();
    }

    //根据claims生成token
    public String generalToken(String username) {
        final Claims claims = generateClaims(username);

        final String token = Jwts.builder()
                //压缩jwt
                .compressWith(CompressionCodecs.DEFLATE)
                .setClaims(claims)
                .signWith(secretKey)
                //过期时间
                .setExpiration(generateExpiration())
                .compact();
        return token;
    }

    //验证token
    public boolean verifyToken(String token, UserDetails userDetails) {
        boolean lap = false;
        try {
            lap = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject()
                    .equals(userDetails.getUsername());
        } catch (Exception e) {
            throw new BadCredentialsException("请重新登录验证");
        }
        return lap;
    }

    //token获取username
    public String getUserName(String token) {
        String username = null;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (JwtException ex) {
            System.out.println(ex);
        }
        return username;
    }

    //生成高强度随机密匙
    private static SecretKey getSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    //生成期望时间
    //生成期望时间
    public Date generateExpiration() {
        //返回当前时间再加过期时间
        return new Date(System.currentTimeMillis() + expired * 1000);
    }

    //生成claims
    public Claims generateClaims(String username) {
        final Claims claims = Jwts.claims();
        claims.put("sub", username);
        claims.put("crete", new Date());
        return claims;
    }

}
