package com.hospital.waste.controller;

import com.hospital.waste.entity.WasteRecord;
import com.hospital.waste.service.WasteRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 废物记录控制器
 */
@Tag(name = "废物记录管理")
@RestController
@RequestMapping("/api/v1/waste")
public class WasteRecordController {

    @Autowired
    private WasteRecordService wasteRecordService;

    @Operation(summary = "查询所有废物记录")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<WasteRecord> list = wasteRecordService.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询废物记录")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        WasteRecord record = wasteRecordService.findById(id);
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

    @Operation(summary = "根据废物编码查询")
    @GetMapping("/code/{wasteCode}")
    public ResponseEntity<Map<String, Object>> getByWasteCode(@PathVariable String wasteCode) {
        WasteRecord record = wasteRecordService.findByWasteCode(wasteCode);
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

    @Operation(summary = "创建废物记录")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody WasteRecord record) {
        WasteRecord created = wasteRecordService.create(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新废物记录")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody WasteRecord record) {
        record.setId(id);
        boolean success = wasteRecordService.update(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除废物记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean success = wasteRecordService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据状态查询")
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getByStatus(@PathVariable Integer status) {
        List<WasteRecord> list = wasteRecordService.findByStatus(status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新废物状态")
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @PathVariable Integer status) {
        boolean success = wasteRecordService.updateStatus(id, status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "状态更新成功" : "状态更新失败");
        return ResponseEntity.ok(result);
    }
}
