package com.hospital.waste.controller;

import com.hospital.waste.entity.DisposalRecord;
import com.hospital.waste.service.DisposalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处置记录控制器
 */
@Tag(name = "处置记录管理")
@RestController
@RequestMapping("/api/v1/disposal-record")
public class DisposalRecordController {

    @Autowired
    private DisposalRecordService disposalRecordService;

    @Operation(summary = "查询所有处置记录")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<DisposalRecord> list = disposalRecordService.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询处置记录")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        DisposalRecord record = disposalRecordService.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (record != null) {
            result.put("code", 200);
            result.put("data", record);
        } else {
            result.put("code", 404);
            result.put("message", "记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建处置记录")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody DisposalRecord record) {
        DisposalRecord created = disposalRecordService.create(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新处置记录")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody DisposalRecord record) {
        record.setId(id);
        boolean success = disposalRecordService.update(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除处置记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean success = disposalRecordService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
