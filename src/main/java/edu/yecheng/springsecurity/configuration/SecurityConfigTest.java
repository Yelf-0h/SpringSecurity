package edu.yecheng.springsecurity.configuration;

import edu.yecheng.springsecurity.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;


/**
 * 安全配置测试
 *
 * @author Yefl
 * @date 2022/10/06
 */
@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Resource
    MyUserDetailsService userDetailsService;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置
     *
     * @param auth 身份验证
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


    /**
     * 配置
     *
     * @param http http
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.formLogin() //自定义自己编写的登录页面
                .loginPage("/login.html")   //登录页面设置
                .loginProcessingUrl("/user/login")  //登录访问路径
                .defaultSuccessUrl("/test/index").permitAll() //登录成功之后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/", "/test/hello", "user/login").permitAll() //设置哪些路径可以直接访问，不需要认证
//                .antMatchers("/test/index").hasAuthority("admins") //设置当前路径只有admins权限才可以访问
//                .antMatchers("/test/index").hasAnyAuthority("admins","role") //设置当前路径只有admins或者role权限才可以访问
//                .antMatchers("/test/index").hasRole("jack")  //设置当前路径只有jack角色才可以访问
                .antMatchers("/test/index").hasAnyRole("ajack","yecheng") //设置当前路径只有jack或者yecheng角色才可以访问
                .anyRequest().authenticated()  //表示所有请求都可以访问
                .and().csrf().disable(); //csrf防护
    }
}
