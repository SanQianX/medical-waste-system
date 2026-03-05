package com.hospital.waste.controller;

import com.hospital.waste.entity.StorageRecord;
import com.hospital.waste.service.StorageRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贮存记录管理Controller
 */
@Tag(name = "贮存管理")
@RestController
@RequestMapping("/api/v1/storage")
public class StorageRecordController {

    @Autowired
    private StorageRecordService storageRecordService;

    @Operation(summary = "获取贮存记录列表")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<StorageRecord> list = storageRecordService.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询贮存记录")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        StorageRecord record = storageRecordService.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (record != null) {
            result.put("code", 200);
            result.put("data", record);
        } else {
            result.put("code", 404);
            result.put("message", "贮存记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据废物ID查询贮存记录")
    @GetMapping("/waste/{wasteId}")
    public ResponseEntity<Map<String, Object>> getByWasteId(@PathVariable Long wasteId) {
        StorageRecord record = storageRecordService.findByWasteId(wasteId);
        Map<String, Object> result = new HashMap<>();
        if (record != null) {
            result.put("code", 200);
            result.put("data", record);
        } else {
            result.put("code", 404);
            result.put("message", "贮存记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建贮存记录（入库）")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody StorageRecord record) {
        StorageRecord created = storageRecordService.create(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "入库成功");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新贮存记录（出库）")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody StorageRecord record) {
        record.setId(id);
        boolean success = storageRecordService.update(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "出库")
    @PutMapping("/{id}/out")
    public ResponseEntity<Map<String, Object>> outStorage(@PathVariable Long id) {
        boolean success = storageRecordService.outStorage(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "出库成功" : "出库失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据状态查询")
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getByStatus(@PathVariable Integer status) {
        List<StorageRecord> list = storageRecordService.findByStatus(status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }
}
