package com.hospital.waste.controller;

import com.hospital.waste.entity.SysUser;
import com.hospital.waste.mapper.SysUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 认证控制器 - 处理登录注册
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Map<String, Object> result = new HashMap<>();

        if (username == null || username.trim().isEmpty()) {
            result.put("code", 400);
            result.put("message", "用户名不能为空");
            return ResponseEntity.ok(result);
        }
        if (password == null || password.trim().isEmpty()) {
            result.put("code", 400);
            result.put("message", "密码不能为空");
            return ResponseEntity.ok(result);
        }

        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            result.put("code", 401);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }

        // 临时方案：同时支持BCrypt和明文密码比较
        boolean matches = false;
        try {
            // 先尝试BCrypt验证
            if (user.getPassword() != null && user.getPassword().startsWith("$2")) {
                matches = BCrypt.checkpw(password, user.getPassword());
            }
        } catch (Exception e) {
            // BCrypt验证失败，尝试明文比较
        }
        // 如果BCrypt验证失败，尝试明文比较（临时方案）
        if (!matches && password.equals(user.getPassword())) {
            matches = true;
        }

        if (!matches) {
            result.put("code", 401);
            result.put("message", "密码错误");
            return ResponseEntity.ok(result);
        }

        // 生成简单的token
        String token = UUID.randomUUID().toString();

        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("token", token);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("realName", user.getRealName());
        userInfo.put("roleId", user.getRoleId());
        result.put("user", userInfo);

        return ResponseEntity.ok(result);
    }

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody SysUser user) {
        Map<String, Object> result = new HashMap<>();

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            result.put("code", 400);
            result.put("message", "用户名不能为空");
            return ResponseEntity.ok(result);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            result.put("code", 400);
            result.put("message", "密码不能为空");
            return ResponseEntity.ok(result);
        }
        if (user.getRealName() == null || user.getRealName().trim().isEmpty()) {
            result.put("code", 400);
            result.put("message", "真实姓名不能为空");
            return ResponseEntity.ok(result);
        }

        // 检查用户名是否已存在
        SysUser existing = sysUserMapper.selectByUsername(user.getUsername());
        if (existing != null) {
            result.put("code", 400);
            result.put("message", "用户名已存在");
            return ResponseEntity.ok(result);
        }

        // 设置默认角色
        if (user.getRoleId() == null) {
            user.setRoleId(2L); // 默认普通用户角色
        }
        // 设置默认状态
        user.setStatus(1);
        // 加密密码
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        sysUserMapper.insert(user);

        result.put("code", 200);
        result.put("message", "注册成功");

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("realName", user.getRealName());
        result.put("data", data);

        return ResponseEntity.ok(result);
    }

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader(value = "X-User-Id", required = false) Long userId) {
        Map<String, Object> result = new HashMap<>();

        if (userId == null) {
            result.put("code", 401);
            result.put("message", "未登录");
            return ResponseEntity.ok(result);
        }

        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("realName", user.getRealName());
        data.put("roleId", user.getRoleId());
        data.put("phone", user.getPhone());
        data.put("departmentId", user.getDepartmentId());

        result.put("code", 200);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }
}
