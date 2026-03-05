package com.hospital.waste.controller;

import com.hospital.waste.entity.SysWarning;
import com.hospital.waste.mapper.SysWarningMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预警管理控制器
 */
@Tag(name = "预警管理")
@RestController
@RequestMapping("/api/v1/warning")
public class WarningController {

    @Autowired
    private SysWarningMapper sysWarningMapper;

    @Operation(summary = "查询所有预警记录")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<SysWarning> list = sysWarningMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询预警记录")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        SysWarning warning = sysWarningMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (warning != null) {
            result.put("code", 200);
            result.put("data", warning);
        } else {
            result.put("code", 404);
            result.put("message", "记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建预警记录")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody SysWarning warning) {
        sysWarningMapper.insert(warning);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", warning);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新预警记录")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody SysWarning warning) {
        warning.setId(id);
        int rows = sysWarningMapper.update(warning);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除预警记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = sysWarningMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据状态查询")
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getByStatus(@PathVariable Integer status) {
        List<SysWarning> list = sysWarningMapper.selectByStatus(status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "查询未处理预警")
    @GetMapping("/unprocessed")
    public ResponseEntity<Map<String, Object>> getUnprocessed() {
        List<SysWarning> list = sysWarningMapper.selectUnprocessed();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }
}
