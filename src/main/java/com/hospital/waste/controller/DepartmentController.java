package com.hospital.waste.controller;

import com.hospital.waste.entity.Department;
import com.hospital.waste.mapper.DepartmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科室管理Controller
 */
@Tag(name = "科室管理")
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Operation(summary = "获取科室列表")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<Department> list = departmentMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询科室")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Department department = departmentMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (department != null) {
            result.put("code", 200);
            result.put("data", department);
        } else {
            result.put("code", 404);
            result.put("message", "科室不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建科室")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Department department) {
        int rows = departmentMapper.insert(department);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "创建成功" : "创建失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新科室")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Department department) {
        department.setId(id);
        int rows = departmentMapper.update(department);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除科室")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = departmentMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
