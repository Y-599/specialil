package lzy.special.special.common.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Method;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity// 开启WebSecurity模式
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则                                  //首页所有人都在请求
        http.cors().and().csrf().disable().authorizeRequests()
        //跨域请求检索
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        //放开登录页面
        .antMatchers("/tologin").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();
;
        // 登陆表单提交请求;
        http.formLogin();
//                .usernameParameter("username")
////                .passwordParameter("password")
////                .loginPage("/toLogin")
////                .loginProcessingUrl("/login");


        http.logout().logoutSuccessUrl("/");
        //开启自动配置的注销的功能
        // /logout 注销请求
        // .logoutSuccessUrl("/"); 注销成功来到首页



        //记住我
        http.rememberMe().rememberMeParameter("remember");
    }

    @Override
    //定制认证规则
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider();
    }
}
