package com.hospital.waste.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 - 验证用户是否已登录
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取 token
        String token = request.getHeader("X-Token");
        if (token == null || token.isEmpty()) {
            // 尝试从参数获取
            token = request.getParameter("token");
        }

        // 获取用户信息
        String userId = request.getHeader("X-User-Id");
        if (userId == null || userId.isEmpty()) {
            userId = request.getParameter("userId");
        }

        // 检查是否已登录（通过检查 localStorage 中的 token 存在性，前端会在请求头中带上）
        if (token != null && !token.isEmpty() && userId != null && !userId.isEmpty()) {
            return true;
        }

        // 对于登录页面、注册页面、静态资源和API请求，放行
        String uri = request.getRequestURI();
        if (uri.contains("/page/login") ||
            uri.contains("/page/register") ||
            uri.contains("/api/v1/auth") ||
            uri.endsWith(".css") ||
            uri.endsWith(".js") ||
            uri.endsWith(".png") ||
            uri.endsWith(".jpg") ||
            uri.endsWith(".ico") ||
            uri.contains("/swagger") ||
            uri.contains("/v3/api-docs") ||
            uri.startsWith("/page/")) {
            return true;
        }

        // 未登录，重定向到登录页
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<script>alert('请先登录');location.href='/page/login';</script>");
        return false;
    }
}
