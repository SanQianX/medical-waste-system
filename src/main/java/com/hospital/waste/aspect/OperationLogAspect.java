package com.hospital.waste.aspect;

import com.hospital.waste.entity.OperationLog;
import com.hospital.waste.mapper.OperationLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 操作日志切面 - 自动记录Controller操作
 */
@Aspect
@Component
public class OperationLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 定义切入点：拦截所有Controller的增删改操作
     */
    @Pointcut("execution(* com.hospital.waste.controller..*.*(..))")
    public void controllerPointcut() {}

    /**
     * 环绕通知：记录操作日志
     */
    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 只记录POST、PUT、DELETE请求
        if (!"POST".equalsIgnoreCase(method) &&
            !"PUT".equalsIgnoreCase(method) &&
            !"DELETE".equalsIgnoreCase(method)) {
            return joinPoint.proceed();
        }

        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method methodObj = signature.getMethod();

        // 获取操作模块和操作类型
        String module = getModuleName(uri);
        String operation = getOperationName(methodObj.getName());

        // 执行方法
        Object result = null;
        boolean success = true;
        String errorMsg = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            success = false;
            errorMsg = e.getMessage();
            throw e;
        } finally {
            // 记录操作日志
            try {
                long duration = System.currentTimeMillis() - startTime;

                OperationLog log = new OperationLog();
                log.setOperationType(operation);
                log.setModule(module);
                log.setOperationDesc(getOperationDesc(methodObj, joinPoint.getArgs()));
                log.setOperatorId(getUserId(request));
                log.setOperationTime(LocalDateTime.now());
                log.setIpAddress(getClientIp(request));
                log.setStatus(success ? 1 : 0);
                log.setDuration(duration);
                log.setRequestUri(uri);
                log.setRequestMethod(method);

                operationLogMapper.insert(log);
                logger.info("操作日志记录成功: {} {} - {} - {}ms",
                    method, uri, module + "-" + operation, duration);
            } catch (Exception e) {
                logger.error("记录操作日志失败: {}", e.getMessage());
            }
        }

        return result;
    }

    /**
     * 从URI中提取模块名称
     */
    private String getModuleName(String uri) {
        if (uri.contains("/department")) return "科室管理";
        if (uri.contains("/user")) return "用户管理";
        if (uri.contains("/role")) return "角色管理";
        if (uri.contains("/waste-category")) return "废物类别";
        if (uri.contains("/waste")) return "废物管理";
        if (uri.contains("/container")) return "容器管理";
        if (uri.contains("/warehouse")) return "仓库管理";
        if (uri.contains("/transfer")) return "转运管理";
        if (uri.contains("/storage")) return "贮存管理";
        if (uri.contains("/disposal")) return "处置管理";
        if (uri.contains("/warning")) return "预警管理";
        if (uri.contains("/fee")) return "费用管理";
        if (uri.contains("/statistics")) return "统计管理";
        if (uri.contains("/auth")) return "认证管理";
        return "其他";
    }

    /**
     * 从HTTP方法获取操作名称
     */
    private String getOperationName(String methodName) {
        if (methodName.startsWith("create") || methodName.startsWith("add") || methodName.startsWith("insert")) {
            return "新增";
        }
        if (methodName.startsWith("update") || methodName.startsWith("edit")) {
            return "更新";
        }
        if (methodName.startsWith("delete") || methodName.startsWith("remove")) {
            return "删除";
        }
        return "操作";
    }

    /**
     * 获取操作描述
     */
    private String getOperationDesc(Method method, Object[] args) {
        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }

    /**
     * 从请求头获取用户ID
     */
    private Long getUserId(HttpServletRequest request) {
        String userId = request.getHeader("X-User-Id");
        if (userId != null && !userId.isEmpty()) {
            try {
                return Long.parseLong(userId);
            } catch (NumberFormatException e) {
                // 忽略
            }
        }
        return null;
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果有多个代理，取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
