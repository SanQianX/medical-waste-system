package com.hospital.waste.controller;

import com.hospital.waste.entity.SysUser;
import com.hospital.waste.mapper.SysUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<SysUser> list = sysUserMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        SysUser user = sysUserMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            result.put("code", 200);
            result.put("data", user);
        } else {
            result.put("code", 404);
            result.put("message", "用户不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据用户名查询")
    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getByUsername(@PathVariable String username) {
        SysUser user = sysUserMapper.selectByUsername(username);
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            result.put("code", 200);
            result.put("data", user);
        } else {
            result.put("code", 404);
            result.put("message", "用户不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody SysUser user) {
        int rows = sysUserMapper.insert(user);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "创建成功" : "创建失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        int rows = sysUserMapper.update(user);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = sysUserMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
