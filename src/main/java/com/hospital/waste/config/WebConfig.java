package com.hospital.waste.config;

import com.hospital.waste.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类 - 注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册认证拦截器
        registry.addInterceptor(authInterceptor)
                // 拦截所有页面请求
                .addPathPatterns("/page/**")
                // 排除不需要拦截的路径
                .excludePathPatterns(
                        "/page/login",
                        "/page/register",
                        "/page/dashboard"
                );
    }
}
