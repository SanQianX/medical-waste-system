package com.hospital.waste.controller;

import com.hospital.waste.entity.SysRole;
import com.hospital.waste.mapper.SysRoleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理控制器
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Operation(summary = "查询所有角色")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<SysRole> list = sysRoleMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询角色")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        SysRole role = sysRoleMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (role != null) {
            result.put("code", 200);
            result.put("data", role);
        } else {
            result.put("code", 404);
            result.put("message", "角色不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据角色编码查询")
    @GetMapping("/code/{roleCode}")
    public ResponseEntity<Map<String, Object>> getByRoleCode(@PathVariable String roleCode) {
        SysRole role = sysRoleMapper.selectByRoleCode(roleCode);
        Map<String, Object> result = new HashMap<>();
        if (role != null) {
            result.put("code", 200);
            result.put("data", role);
        } else {
            result.put("code", 404);
            result.put("message", "角色不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody SysRole role) {
        sysRoleMapper.insert(role);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", role);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody SysRole role) {
        role.setId(id);
        int rows = sysRoleMapper.update(role);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = sysRoleMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
