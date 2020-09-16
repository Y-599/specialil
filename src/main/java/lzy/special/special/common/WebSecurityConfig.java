package lzy.special.special.common;


import lzy.special.special.common.security.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private UserDetailsService userDetailsService;

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            // 使用自定义登录身份认证组件     JwtAuthenticationProvider 从数据查询用户的认证流程类
//            auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));

        }
        //认证与授权
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    // 跨域预检请求
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // 登录URL
                    .antMatchers("/admin/**").permitAll()
                    .antMatchers("/login").permitAll()

                    // swagger
                    .antMatchers("/swagger**/**").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/v2/**").permitAll()
                    // 其他所有请求需要身份认证
                    .anyRequest().authenticated();
            // 退出登录处理器
            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
            // 开启登录认证流程过滤器  注释后将会走自定义认证  也就是手动动用认证接口
           // http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
            // 访问控制时登录状态检查过滤器
//            http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }

    }

