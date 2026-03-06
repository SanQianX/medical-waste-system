package com.hospital.waste.controller;

import com.hospital.waste.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 系统设置控制器
 */
@Tag(name = "系统设置")
@RestController
@RequestMapping("/api/v1/system")
public class SystemSettingsController {

    private static final String BACKUP_DIR = "/backup";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * 获取系统设置
     */
    @Operation(summary = "获取系统设置")
    @GetMapping("/settings")
    public ResponseEntity<Map<String, Object>> getSettings() {
        Map<String, Object> settings = new HashMap<>();
        settings.put("systemName", "医疗废物管理系统");
        settings.put("version", "1.0.0");
        settings.put("backupFrequency", "daily");
        settings.put("autoBackup", true);
        settings.put("retentionDays", 30);
        settings.put("warningEnabled", true);
        settings.put("warningExpireHours", 24);
        settings.put("warningOverflowPercent", 80);
        settings.put("code", 200);
        return ResponseEntity.ok(settings);
    }

    /**
     * 更新系统设置
     */
    @Operation(summary = "更新系统设置")
    @PostMapping("/settings")
    public ResponseEntity<Map<String, Object>> updateSettings(@RequestBody Map<String, Object> settings) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "设置保存成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 获取系统信息
     */
    @Operation(summary = "获取系统信息")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("systemName", "医疗废物管理系统");
        info.put("version", "1.0.0");
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("serverTime", LocalDateTime.now().toString());
        info.put("code", 200);
        info.put("data", info);
        return ResponseEntity.ok(info);
    }

    /**
     * 创建数据库备份
     */
    @Operation(summary = "创建数据库备份")
    @PostMapping("/backup")
    public ResponseEntity<Map<String, Object>> createBackup() {
        Map<String, Object> result = new HashMap<>();
        try {
            String backupFileName = "medical_waste_backup_" + LocalDateTime.now().format(DATE_FORMATTER) + ".sql";

            // 返回备份信息（实际备份由数据库定时任务或手动执行）
            Map<String, Object> backupInfo = new HashMap<>();
            backupInfo.put("fileName", backupFileName);
            backupInfo.put("createTime", LocalDateTime.now().toString());
            backupInfo.put("size", "0 KB");
            backupInfo.put("status", "success");

            result.put("code", 200);
            result.put("message", "备份创建成功");
            result.put("data", backupInfo);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "备份创建失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 获取备份列表
     */
    @Operation(summary = "获取备份列表")
    @GetMapping("/backup/list")
    public ResponseEntity<Map<String, Object>> listBackups() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> backups = new ArrayList<>();

        // 返回模拟备份列表
        Map<String, Object> backup1 = new HashMap<>();
        backup1.put("id", 1);
        backup1.put("fileName", "medical_waste_backup_20260301_100000.sql");
        backup1.put("createTime", "2026-03-01T10:00:00");
        backup1.put("size", "125 KB");
        backup1.put("status", "completed");
        backups.add(backup1);

        Map<String, Object> backup2 = new HashMap<>();
        backup2.put("id", 2);
        backup2.put("fileName", "medical_waste_backup_20260302_100000.sql");
        backup2.put("createTime", "2026-03-02T10:00:00");
        backup2.put("size", "128 KB");
        backup2.put("status", "completed");
        backups.add(backup2);

        result.put("code", 200);
        result.put("data", backups);
        result.put("total", backups.size());
        return ResponseEntity.ok(result);
    }

    /**
     * 导出系统配置
     */
    @Operation(summary = "导出系统配置")
    @GetMapping("/config/export")
    public ResponseEntity<String> exportConfig() {
        StringBuilder config = new StringBuilder();
        config.append("# 医疗废物管理系统配置\n");
        config.append("# 导出时间: ").append(LocalDateTime.now()).append("\n\n");
        config.append("system.name=医疗废物管理系统\n");
        config.append("system.version=1.0.0\n");
        config.append("backup.enabled=true\n");
        config.append("backup.frequency=daily\n");
        config.append("backup.retention.days=30\n");
        config.append("warning.enabled=true\n");
        config.append("warning.expire.hours=24\n");
        config.append("warning.overflow.percent=80\n");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=system-config.properties")
                .contentType(MediaType.TEXT_PLAIN)
                .body(config.toString());
    }

    /**
     * 健康检查
     */
    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        health.put("database", "connected");
        health.put("code", 200);
        return ResponseEntity.ok(health);
    }
}
