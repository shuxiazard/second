package com.example.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        //密匙
        final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        final JwtBuilder jw = Jwts.builder();
        final String s = jw
                //加密内容 claims
                //.setSubject("shuxia")
                .claim("username","shuxia")
                //签名
                .signWith(secretKey)
                .compact();
        System.out.println(s);
        final boolean a = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(s).getBody().getSubject().equals("a");
        System.out.println(a);
    }

}
