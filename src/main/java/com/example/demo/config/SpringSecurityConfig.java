package com.example.demo.config;

<<<<<<< HEAD
import com.example.demo.common.JwtFilter;
=======
import com.example.demo.component.JwtFilter;
>>>>>>> main
import com.example.demo.sec.service.impl.MyUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSercurity
 *
 * @author 彭彭
 * @date 7/3/2021
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
<<<<<<< HEAD
                .antMatchers("/login", "/**")
                .permitAll()
                .anyRequest().authenticated()
                // 关闭跨站请求防护及不使用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        ;
        // 禁用缓存
        //http.headers().cacheControl();
        // 添加JWT filter
       // http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
=======
                .antMatchers("/login","/register")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/index")
                .permitAll()
                .antMatchers("/user/**")
                .hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
               /* 关闭session会使principal会为空，使用token就是替代session，结果报错，未知原因*/
               //.sessionManagement()
               // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT filter
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
>>>>>>> main
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
<<<<<<< HEAD
    @Bean
=======
>>>>>>> main
    public UserDetailsService userDetailsService() {
        return new MyUserDetailServiceImpl();
    }

<<<<<<< HEAD

=======
    @Bean
    public JwtFilter jwtFilter(){return new JwtFilter();}

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
>>>>>>> main
}
