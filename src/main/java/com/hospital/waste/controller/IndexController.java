package com.hospital.waste.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 根路径控制器 - 提供欢迎页面和健康检查
 */
@Tag(name = "系统首页")
@RestController
public class IndexController {

    @Operation(summary = "系统首页")
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @Operation(summary = "健康检查")
    @GetMapping("/api/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(result);
    }
}
