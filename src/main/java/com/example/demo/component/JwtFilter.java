package com.example.demo.component;

import com.example.demo.component.utils.JwtUtils;
import com.example.demo.sec.service.impl.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * JwtFilter
 *
 * @author shuxia
 * @date 7/4/2021
 */

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    MyUserDetailServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头
        final String header = request.getHeader(tokenHeader);
        if (header!=null&&header.startsWith(tokenHead)) {
            //获取token
            final String token = header.substring(this.tokenHead.length());
            //token获取用户名
            final String userName = jwtUtils.getUserName(token);
            //安全上下文是否存在信息
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (userName != null && auth == null) {
                //从数据库获取用户名
                final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                //验证token
                final boolean b = jwtUtils.verifyToken(token, userDetails);
                if (b) {
                    //创建令牌,注意参数authorities，放错位置会变成匿名访问
                    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //创建空的安全上下文 防止多线程出现问题
                    final SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                    emptyContext.setAuthentication(authenticationToken);
                    //设置安全上下文
                    SecurityContextHolder.setContext(emptyContext);
                    final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
                }
            }
        }
        filterChain.doFilter(request,response);

    }
}
