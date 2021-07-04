# 工程简介


# 延伸阅读

## JWT

JWT要配合前端，每个请求头都需要带token。已经通过postman测试权限，需要开启jwt在SpringSecurityConfig
关闭session即可

```java
protected void configure(HttpSecurity http) throws Exception {
    http.
         ...
           .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
```

![image-20210704234341885](C:\Users\彭彭\AppData\Roaming\Typora\typora-user-images\image-20210704234341885.png)