package com.hospital.waste.controller;

import com.hospital.waste.entity.TransferRecord;
import com.hospital.waste.service.TransferRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转运记录控制器
 */
@RestController
@RequestMapping("/api/v1/transfer")
@Tag(name = "转运记录管理", description = "转运记录相关接口")
public class TransferRecordController {

    @Autowired
    private TransferRecordService transferRecordService;

    /**
     * 查询所有转运记录
     */
    @GetMapping("/list")
    @Operation(summary = "获取转运记录列表", description = "查询所有转运记录")
    public ResponseEntity<Map<String, Object>> list() {
        List<TransferRecord> list = transferRecordService.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取转运记录详情", description = "根据ID查询转运记录")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        TransferRecord record = transferRecordService.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (record != null) {
            result.put("code", 200);
            result.put("message", "success");
            result.put("data", record);
        } else {
            result.put("code", 404);
            result.put("message", "记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据转运单号查询
     */
    @GetMapping("/no/{transferNo}")
    @Operation(summary = "根据转运单号查询", description = "根据转运单号查询转运记录")
    public ResponseEntity<Map<String, Object>> getByTransferNo(@PathVariable String transferNo) {
        TransferRecord record = transferRecordService.findByTransferNo(transferNo);
        Map<String, Object> result = new HashMap<>();
        if (record != null) {
            result.put("code", 200);
            result.put("message", "success");
            result.put("data", record);
        } else {
            result.put("code", 404);
            result.put("message", "记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 创建转运记录
     */
    @PostMapping
    @Operation(summary = "创建转运记录", description = "创建新的转运记录")
    public ResponseEntity<Map<String, Object>> create(@RequestBody TransferRecord record) {
        TransferRecord created = transferRecordService.create(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    /**
     * 更新转运记录
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新转运记录", description = "更新转运记录信息")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody TransferRecord record) {
        record.setId(id);
        boolean success = transferRecordService.update(record);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除转运记录
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除转运记录", description = "根据ID删除转运记录")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean success = transferRecordService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据状态查询
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态查询", description = "根据状态查询转运记录")
    public ResponseEntity<Map<String, Object>> getByStatus(@PathVariable Integer status) {
        List<TransferRecord> list = transferRecordService.findByStatus(status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    /**
     * 开始转运
     */
    @PutMapping("/{id}/start")
    @Operation(summary = "开始转运", description = "更新状态为运输中")
    public ResponseEntity<Map<String, Object>> startTransfer(@PathVariable Long id) {
        boolean success = transferRecordService.startTransfer(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "已开始转运" : "操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 到达目的地
     */
    @PutMapping("/{id}/arrive")
    @Operation(summary = "到达目的地", description = "更新状态为已到达")
    public ResponseEntity<Map<String, Object>> arrive(@PathVariable Long id) {
        boolean success = transferRecordService.arrive(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "已到达目的地" : "操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 完成转运
     */
    @PutMapping("/{id}/complete")
    @Operation(summary = "完成转运", description = "更新状态为已完成")
    public ResponseEntity<Map<String, Object>> complete(@PathVariable Long id) {
        boolean success = transferRecordService.complete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "转运已完成" : "操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 更新转运状态
     */
    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "更新转运状态", description = "更新转运记录的状态")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @PathVariable Integer status) {
        boolean success = transferRecordService.updateStatus(id, status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "状态更新成功" : "状态更新失败");
        return ResponseEntity.ok(result);
    }
}
