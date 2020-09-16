package lzy.special.special.common.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lzy.special.special.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * UsernamePasswordAuthenticationFilter：用于处理基于表单的登录请求，从表单中获取用户名和密码。默认情况下处理来自
 * /login 的请求。从表单中获取用户名和密码时，默认使用的表单 name 值为 username 和 password，这两个值可以通过设置这
 * 个过滤器的usernameParameter 和 passwordParameter 两个参数的值进行修改。
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * JwtLoginFilter extends UsernamePasswordAuthenticationFilter
     * 1. 覆写认证方法，修改用户名、密码的获取方式，具体原因看代码注释
     *
     * 2. 覆写认证成功后的操作，移除后台跳转，添加生成令牌并返回给客户端
     */
    public JwtLoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager); //设置身份验证管理器
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // POST 请求 /login 登录时拦截， 由此方法触发执行登录认证流程，可以在此覆写整个登录认证逻辑
        super.doFilter(req, res, chain);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //重写了用户的认证方法
        // 可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
        // 如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
        // 此过滤器的用户名密码默认从request.getParameter()获取，但是这种
        // 读取方式不能读取到如 application/json 等 post 请求数据，需要把
        // 用户名密码的读取逻辑修改为到流中读取request.getInputStream()
        String  a  = request.getParameter("username");
        String body = getBody(request);
        JSONObject jsonObject = JSON.parseObject(body);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        JwtAuthenticatioToken authRequest = new JwtAuthenticatioToken(username, password);
        System.out.println(authRequest);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 存储登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // 记住我服务
        getRememberMeServices().loginSuccess(request, response, authResult);
        // 触发事件监听器
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        // 生成并返回token给客户端，后续访问携带此token
//        JwtAuthenticatioToken token = new JwtAuthenticatioToken(null, null, JwtTokenUtils.generateToken(authResult));
//        HttpUtils.write(response, token);
    }

    /**
     * 获取请求Body
     * @param request
     * @return
     */
    private String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
