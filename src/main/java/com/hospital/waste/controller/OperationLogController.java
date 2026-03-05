package com.hospital.waste.controller;

import com.hospital.waste.entity.OperationLog;
import com.hospital.waste.mapper.OperationLogMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志控制器
 */
@Tag(name = "操作日志")
@RestController
@RequestMapping("/api/v1/operation-log")
public class OperationLogController {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Operation(summary = "查询所有操作日志")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<OperationLog> list = operationLogMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询操作日志")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        OperationLog log = operationLogMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (log != null) {
            result.put("code", 200);
            result.put("data", log);
        } else {
            result.put("code", 404);
            result.put("message", "日志不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据用户ID查询")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getByUserId(@PathVariable Long userId) {
        List<OperationLog> list = operationLogMapper.selectByUserId(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据模块查询")
    @GetMapping("/module/{module}")
    public ResponseEntity<Map<String, Object>> getByModule(@PathVariable String module) {
        List<OperationLog> list = operationLogMapper.selectByModule(module);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据时间范围查询")
    @GetMapping("/date-range")
    public ResponseEntity<Map<String, Object>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<OperationLog> list = operationLogMapper.selectByDateRange(startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除历史日志")
    @DeleteMapping("/cleanup")
    public ResponseEntity<Map<String, Object>> cleanup(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beforeTime) {
        int rows = operationLogMapper.deleteByDateRange(beforeTime);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "已清理 " + rows + " 条历史日志");
        return ResponseEntity.ok(result);
    }
}
